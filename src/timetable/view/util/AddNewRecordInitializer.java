package timetable.view.util;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import components.MyComboBoxModel;
import hibernateFiles.entity.Chair;
import hibernateFiles.entity.Classroom;
import hibernateFiles.entity.Group;
import hibernateFiles.entity.Lecturer;
import hibernateFiles.entity.Record;
import hibernateFiles.entity.Subject;
import hibernateFiles.entity.SubjectFor;
import hibernateFiles.entity.SubjectType;
import hibernateFiles.entity.builder.RecordBuilder;
import service.exception.ServiceException;
import service.factory.IServiceFactory;
import service.factory.ServiceFactoryName;
import service.factory.ServiceFactoryProvider;
import timetable.util.ActionMode;
import timetable.util.Checker;
import timetable.util.LessonFor;
import timetable.util.LessonPeriod;
import timetable.util.LessonType;
import timetable.util.WeekNumber;
import timetable.view.AddNewRecordForm;

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

		form.setParent(parent);
		form.setAction(ActionMode.ADD);

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

		IServiceFactory factory = ServiceFactoryProvider.getInstance().getServiceFactory(ServiceFactoryName.CHOKE);

		List<Chair> chairsList = factory.getChairService().getAllChairs();

		DefaultComboBoxModel<Chair> model = new MyComboBoxModel<>(chairsList);
		chairComboBox.setModel(model);
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

		IServiceFactory factory = ServiceFactoryProvider.getInstance().getServiceFactory(ServiceFactoryName.CHOKE);

		List<Subject> subjectsList = factory.getSubjectService().getSubjectsRecordsByChairIdAndEduLevel(
				(Chair) chairComboBox.getSelectedItem(), form.getAddRecord().getGroup().getEduLevel());

		DefaultComboBoxModel<Subject> model = new MyComboBoxModel<>(subjectsList);
		subjectComboBox.setModel(model);
		if (!subjectsList.isEmpty()) {
			subjectComboBox.setSelectedIndex(0);
		} else {
			subjectComboBox.setSelectedIndex(-1);
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

		IServiceFactory factory = ServiceFactoryProvider.getInstance().getServiceFactory(ServiceFactoryName.CHOKE);

		List<Lecturer> lecturersList = factory.getLecturerService()
				.getLecturersRecordsByChairId((Chair) chairComboBox.getSelectedItem());

		DefaultComboBoxModel<Lecturer> model = new MyComboBoxModel<>(lecturersList);
		lecturerComboBox.setModel(model);
		if (!lecturersList.isEmpty()) {
			lecturerComboBox.setSelectedIndex(0);
		} else {
			lecturerComboBox.setSelectedIndex(-1);
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

		IServiceFactory factory = ServiceFactoryProvider.getInstance().getServiceFactory(ServiceFactoryName.CHOKE);

		List<Classroom> classroomsList = factory.getClassroomService().getClassroomList();

		DefaultComboBoxModel<Classroom> model = new MyComboBoxModel<>(classroomsList);
		classroomComboBox.setModel(model);
	}
}