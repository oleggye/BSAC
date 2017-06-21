package by.bsac.timetable.view.util;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import by.bsac.timetable.hibernateFiles.HibernateUtil;
import by.bsac.timetable.hibernateFiles.entity.Chair;
import by.bsac.timetable.hibernateFiles.entity.Classroom;
import by.bsac.timetable.hibernateFiles.entity.Group;
import by.bsac.timetable.hibernateFiles.entity.Lecturer;
import by.bsac.timetable.hibernateFiles.entity.Record;
import by.bsac.timetable.hibernateFiles.entity.Subject;
import by.bsac.timetable.hibernateFiles.entity.SubjectFor;
import by.bsac.timetable.hibernateFiles.entity.SubjectType;
import by.bsac.timetable.hibernateFiles.entity.builder.RecordBuilder;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.factory.IServiceFactory;
import by.bsac.timetable.service.factory.ServiceFactoryName;
import by.bsac.timetable.service.factory.ServiceFactoryProvider;
import by.bsac.timetable.util.ActionMode;
import by.bsac.timetable.util.Checker;
import by.bsac.timetable.util.LessonFor;
import by.bsac.timetable.util.LessonPeriod;
import by.bsac.timetable.util.LessonType;
import by.bsac.timetable.util.WeekNumber;
import by.bsac.timetable.view.AddNewRecordForm;
import components.MyComboBoxModel;

public final class AddNewRecordInitializer {

	private AddNewRecordForm form;

	public AddNewRecordInitializer(AddNewRecordForm frame) {
		this.form = frame;
	}

	/**
	 * Method for initializing form field if we run form in adding mode
	 * 
	 * @param parent
	 * @param lessonDate
	 * @param group
	 * @param weekNumber
	 * @param weekDay
	 * @param lessonOrdinalNumber
	 */
	public void initFormFieldList(JFrame parent, Date lessonDate, Group group, byte weekNumber, byte weekDay,
			byte lessonOrdinalNumber) {

		HibernateUtil.getSession();
		form.setParent(parent);
		form.setAction(ActionMode.Add_Record);

		form.setLessonPeriod(LessonPeriod.FOR_ONE_DATE);

		if (Checker.isGroupInFlow(group)) {
			form.setLessonFor(LessonFor.FULL_FLOW);
		} else {
			form.setLessonFor(LessonFor.FULL_GROUP);
		}
		form.setLessonType(LessonType.LECTURE);

		form.setCurrentWeekNumber(WeekNumber.getWeekNumberInstance(weekNumber));
		form.getWeekSet().add(form.getCurrentWeekNumber());
		form.setCurrentWeekDay(weekDay);
		form.setLessonOrdinalNumber(lessonOrdinalNumber);
		form.setEdu_level(group.getEduLevel());

		SubjectType subjectType = form.getLessonType().lessonTypeToSubjectType();
		SubjectFor subjectFor = form.getLessonFor().lessonForToSubjectFor();

		Record addRecord = new RecordBuilder().buildDateFrom(lessonDate).buildDateTo(lessonDate).buildGroup(group)
				.buildSubjectFor(subjectFor).buildSubjectType(subjectType).buildWeekNumber(weekNumber)
				.buildWeekDay(weekDay).buildSubjOrdinalNumber(lessonOrdinalNumber).build();
		form.setAddRecord(addRecord);
		HibernateUtil.closeSession();
	}

	/**
	 * Method instantiate a new instance of {@link JDatePickerImpl} with certain
	 * date
	 * 
	 * @param date
	 * @return
	 */
	public JDatePickerImpl initDatePicker(Date date) {
		/* задаем дату для календаря в качестве опорной */

		UtilDateModel model = new UtilDateModel();
		model.setValue(date);

		Properties p = new Properties();
		p.put("text.today", "Сегодня");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		return new JDatePickerImpl(datePanel, new supportClasses.DateLabelFormatter());
	}

	/**
	 * Method gets the list of {@link Chair} instances and inits JComboBox param
	 * 
	 * @param chairComboBox
	 * @throws ServiceException
	 */
	public void initChairComboBox(JComboBox<Chair> chairComboBox) throws ServiceException {

		HibernateUtil.getSession();
		try {
			IServiceFactory factory = ServiceFactoryProvider.getInstance().getServiceFactory(ServiceFactoryName.CHOKE);

			List<Chair> chairsList = factory.getChairService().getAllChair();

			DefaultComboBoxModel<Chair> model = new MyComboBoxModel<>(chairsList);
			chairComboBox.setModel(model);
		} finally {
			HibernateUtil.closeSession();
		}
	}

	/**
	 * Method gets the list of {@link Subject} instances and inits JComboBox
	 * param
	 * 
	 * @param subjectComboBox
	 * @param chairComboBox
	 * @throws ServiceException
	 */
	public void initSubjectComboBox(JComboBox<Subject> subjectComboBox, JComboBox<Chair> chairComboBox)
			throws ServiceException {

		HibernateUtil.getSession();
		try {
			IServiceFactory factory = ServiceFactoryProvider.getInstance().getServiceFactory();

			Chair selectedChair = (Chair) chairComboBox.getSelectedItem();
			byte educationLevel = form.getAddRecord().getGroup().getEduLevel();
			List<Subject> subjectsList = factory.getSubjectService()
					.getSubjectsRecordsByChairIdAndEduLevel(selectedChair, educationLevel);

			subjectComboBox.removeAllItems();

			if (!subjectsList.isEmpty()) {
				DefaultComboBoxModel<Subject> model = new MyComboBoxModel<>(subjectsList);
				subjectComboBox.setModel(model);
			}
		} finally {
			HibernateUtil.closeSession();
		}
	}

	/**
	 * Method gets the list of {@link Lecturer} instances and inits JComboBox
	 * param
	 * 
	 * @param lecturerComboBox
	 * @param chairComboBox
	 * @throws ServiceException
	 */
	public void initLecturerComboBox(JComboBox<Lecturer> lecturerComboBox, JComboBox<Chair> chairComboBox)
			throws ServiceException {

		HibernateUtil.getSession();
		try {
			IServiceFactory factory = ServiceFactoryProvider.getInstance().getServiceFactory();

			List<Lecturer> lecturersList = factory.getLecturerService()
					.getLecturersRecordsByChairId((Chair) chairComboBox.getSelectedItem());

			lecturerComboBox.removeAllItems();
			if (!lecturersList.isEmpty()) {
				DefaultComboBoxModel<Lecturer> model = new MyComboBoxModel<>(lecturersList);
				lecturerComboBox.setModel(model);
			}
		} finally {
			HibernateUtil.closeSession();
		}
	}

	/**
	 * Method gets the list of {@link Classroom} instances and inits JComboBox
	 * param
	 * 
	 * @param lecturerComboBox
	 * @param chairComboBox
	 * @throws ServiceException
	 */
	public void initClassroomComboBox(JComboBox<Classroom> classroomComboBox) throws ServiceException {

		HibernateUtil.getSession();
		try {
			IServiceFactory factory = ServiceFactoryProvider.getInstance().getServiceFactory();

			List<Classroom> classroomsList = factory.getClassroomService().getClassroomList();

			DefaultComboBoxModel<Classroom> model = new MyComboBoxModel<>(classroomsList);
			classroomComboBox.setModel(model);
		} finally {
			HibernateUtil.closeSession();
			if (classroomComboBox.getItemCount() > 0) {
				classroomComboBox.setSelectedIndex(0);
			}
		}
	}
}