package timetable.view.util;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import components.MyComboBoxModel;
import hibernateFiles.HibernateUtil;
import hibernateFiles.entity.Chair;
import hibernateFiles.entity.Classroom;
import hibernateFiles.entity.Group;
import hibernateFiles.entity.Lecturer;
import hibernateFiles.entity.Record;
import hibernateFiles.entity.Subject;
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
import timetable.view.UpdateOrCancelForm;

public class UpdateOrCancelInitializer {
	private UpdateOrCancelForm form;

	public UpdateOrCancelInitializer(UpdateOrCancelForm form) {
		this.form = form;
	}

	/**
	 * Method for initializing form field if we run form in editing mode
	 * 
	 * @param parent
	 * @param lessonDate
	 * @param record
	 * @param group
	 * @param weekNumber
	 * @param weekDay
	 * @param lessonOrdinalNumber
	 */
	public void initFormFieldList(JFrame parent, Date lessonDate, Record record, Group group, byte weekNumber,
			byte weekDay, byte lessonOrdinalNumber) {

		HibernateUtil.getSession();

		Record updateRecord, cancelRecord;
		try {
			HibernateUtil.getSession();
			updateRecord = (Record) record.clone();
			cancelRecord = (Record) record.clone();
			HibernateUtil.closeSession();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		form.setParent(parent);
		form.setUpdateRecord(updateRecord);
		form.setCancelRecord(cancelRecord);

		/* разбираемся с датой переданной записи */
		if (Checker.isRecordForOneDate(record)) {
			form.setUpdateLessonPeriod(LessonPeriod.FOR_ONE_DATE);
			form.setCancelLessonPeriod(LessonPeriod.FOR_ONE_DATE);
		} else {
			form.setUpdateLessonPeriod(LessonPeriod.FOR_THE_PERIOD);
			form.setCancelLessonPeriod(LessonPeriod.FOR_THE_PERIOD);
		}

		form.setLessonFor(LessonFor.subjectForToLessonFor(record.getSubjectFor()));
		form.setLessonType(LessonType.subjectTypeToLessonType(record.getSubjectType()));

		form.setAction(ActionMode.Update);

		Set<WeekNumber> updateWeekSet = new HashSet<>();
		Set<WeekNumber> cancelWeekSet = new HashSet<>();
		updateWeekSet.add(WeekNumber.getWeekNumberInstance(weekNumber));
		cancelWeekSet.add(WeekNumber.getWeekNumberInstance(weekNumber));

		form.setUpdateWeekSet(updateWeekSet);
		form.setCancelWeekSet(cancelWeekSet);

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
			IServiceFactory factory = ServiceFactoryProvider.getInstance().getServiceFactory();

			List<Chair> chairsList = factory.getChairService().getAllChairs();

			DefaultComboBoxModel<Chair> model = new MyComboBoxModel<>(chairsList);
			chairComboBox.setModel(model);
			chairComboBox.setSelectedIndex(-1);
			chairComboBox.setSelectedIndex(0);
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
			byte educationLevel = form.getUpdateRecord().getGroup().getEduLevel();
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
			if (lecturerComboBox.getItemCount() > 0) {
				lecturerComboBox.setSelectedIndex(0);
			}
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