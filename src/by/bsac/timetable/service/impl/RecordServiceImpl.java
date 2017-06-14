package by.bsac.timetable.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.dao.factory.DAOFactory;
import by.bsac.timetable.hibernateFiles.entity.Cancellation;
import by.bsac.timetable.hibernateFiles.entity.Group;
import by.bsac.timetable.hibernateFiles.entity.Record;
import by.bsac.timetable.service.IRecordService;
import by.bsac.timetable.service.IValidationService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.service.factory.impl.ServiceFactory;
import supportClasses.DateUtil;
import timetable.util.LessonFor;
import timetable.util.LessonPeriod;

public class RecordServiceImpl implements IRecordService {

	@Override
	public void addRecord(Record record) throws ServiceException, ServiceValidationException {

		IValidationService validationService = ServiceFactory.getInstance().getValidationService();
		validationService.validateRecord(record);

		DAOFactory factory = DAOFactory.getInstance();
		LessonFor lessonFor = LessonFor.subjectForToLessonFor(record.getSubjectFor());
		try {
			/* если это пара для всего потока, то нужно добавить всем */
			if (lessonFor.equals(LessonFor.FULL_FLOW)) {
				addFlowRecord(record);
			} else {
				checkRecordForConflict(record);
				factory.getRecordDAO().add(record);
			}
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при вставке", e);
		}
	}

	@Override
	public void updateRecord(Record initialRecord, Record updateRecord)
			throws ServiceException, ServiceValidationException {

		IValidationService validationService = ServiceFactory.getInstance().getValidationService();
		validationService.validateRecord(updateRecord);

		/*
		 * если это пара на одну дату и дату изменили, то нужно обновить номер
		 * недели и дня
		 */
		if (updateRecord.getDateFrom().equals(updateRecord.getDateTo())
				&& !updateRecord.getDateFrom().equals(initialRecord.getDateFrom())
				&& !updateRecord.getDateTo().equals(initialRecord.getDateTo())) {

			System.out.println(updateRecord.getDateFrom().getClass());
			byte weekDay = DateUtil.getWeekDay(updateRecord.getDateFrom());
			byte weekNumber = DateUtil.getWeekNumber(updateRecord.getDateFrom());
			updateRecord.setWeekDay(weekDay);
			updateRecord.setWeekNumber(weekNumber);
		}

		DAOFactory factory = DAOFactory.getInstance();
		LessonFor lessonFor = LessonFor.subjectForToLessonFor(updateRecord.getSubjectFor());
		try {
			/* если это пара для всего потока, то нужно обновить у всех */
			if (lessonFor.equals(LessonFor.FULL_FLOW)) {
				updateFlowRecord(initialRecord, updateRecord);
			} else {
				factory.getRecordDAO().update(updateRecord);
			}
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при обновлении", e);
		}
	}

	@Override
	public Record getRecord(int id_main) throws ServiceException {

		Record Record = null;
		DAOFactory factory = DAOFactory.getInstance();
		try {
			Record = factory.getRecordDAO().getById(id_main);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении занятия", e);
		}
		return Record;
	}

	@Override
	public void deleteRecord(Record record) throws ServiceException {

		DAOFactory factory = DAOFactory.getInstance();
		try {
			factory.getRecordDAO().delete(record);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении занятия", e);
		}
	}

	@Override
	public List<Record> getAllRecordListForGroup(Group group, Date dateFrom, Date dateTo) throws ServiceException {

		List<Record> mainRecordList = null;
		DAOFactory factory = DAOFactory.getInstance();
		try {
			mainRecordList = factory.getRecordDAO().getRecordListByGroupAndDate(group, dateFrom, dateTo);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении занятия", e);
		}
		return mainRecordList;
	}

	@Override
	public void cancelRecord(Record initialRecord, Record cancelRecord, LessonPeriod cancelLessonPeriod)
			throws ServiceException, ServiceValidationException {

		IValidationService validationService = ServiceFactory.getInstance().getValidationService();
		validationService.validateRecord(cancelRecord);

		DAOFactory factory = DAOFactory.getInstance();
		try {
			/*
			 * удаление по номеру недели или это пара на одну дату, то удаляем
			 * данную запись
			 */
			LessonFor lessonFor = LessonFor.subjectForToLessonFor(cancelRecord.getSubjectFor());
			if (cancelLessonPeriod.equals(LessonPeriod.FOR_WEEK_NUMBER)
					|| initialRecord.getDateFrom().equals(initialRecord.getDateTo())) {

				/* если это пара для всего потока, то нужно обновить у всех */
				if (lessonFor.equals(LessonFor.FULL_FLOW)) {
					deleteFlowRecord(cancelRecord);
				} else {
					factory.getRecordDAO().delete(cancelRecord);
				}

			} else {
				if (lessonFor.equals(LessonFor.FULL_FLOW)) {
					cancelFlowRecord(initialRecord, cancelRecord);
				} else {
					Cancellation cancellation = new Cancellation();
					cancellation.setDateFrom(cancelRecord.getDateFrom());
					cancellation.setDateTo(cancelRecord.getDateTo());
					cancellation.setRecord(initialRecord);
					factory.getCancellationDAO().add(cancellation);
				}
			}
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при отмене занятия", e);
		}
	}

	private void addFlowRecord(Record addRecord) throws DAOException, ServiceException, ServiceValidationException {
		DAOFactory factory = DAOFactory.getInstance();

		List<Record> recordList = new LinkedList<>();

		List<Group> groupList = factory.getGroupDAO().getGroupByFlow(addRecord.getGroup().getFlow());
		for (Group group : groupList) {
			addRecord.setGroup(group);
			// factory.getRecordDAO().add(addRecord);
			try {
				checkRecordForConflict(addRecord);
				recordList.add((Record) addRecord.clone());
			} catch (CloneNotSupportedException e) {
				throw new ServiceException("Ошибка при обновлении записи для потока", e);
			}
		}
		factory.getRecordDAO().addAll(recordList);
	}

	private void updateFlowRecord(Record initialRecord, Record updateRecord)
			throws DAOException, ServiceException, ServiceValidationException {
		DAOFactory factory = DAOFactory.getInstance();

		List<Group> groupList = factory.getGroupDAO().getGroupByFlow(initialRecord.getGroup().getFlow());
		for (Group group : groupList) {
			Record thisGroupRecord = factory.getRecordDAO().getRecordForGroupLikeThis(group, initialRecord);
			if (thisGroupRecord != null) {
				updateRecord.setGroup(group);
				updateRecord.setIdRecord(thisGroupRecord.getIdRecord());
				checkRecordForConflict(updateRecord);
			}
		}

		for (Group group : groupList) {
			Record thisGroupRecord = factory.getRecordDAO().getRecordForGroupLikeThis(group, initialRecord);
			if (thisGroupRecord != null) {
				updateRecord.setGroup(group);
				updateRecord.setIdRecord(thisGroupRecord.getIdRecord());
				factory.getRecordDAO().update(updateRecord);
			}
		}
		// try {
		// checkRecordForConflict(updateRecord);
		// recordList.add((Record) updateRecord.clone());
		// } catch (CloneNotSupportedException e) {
		// throw new ServiceException("Ошибка при обновлении записи для потока",
		// e);
		// }
		// // factory.getRecordDAO().update(updateRecord);
		// }
		// factory.getRecordDAO().updateAll(recordList);
	}

	private void cancelFlowRecord(Record initalRecord, Record cancelRecord) throws DAOException {
		DAOFactory factory = DAOFactory.getInstance();

		Cancellation cancellation = new Cancellation();
		cancellation.setDateFrom(cancelRecord.getDateFrom());
		cancellation.setDateTo(cancelRecord.getDateTo());

		List<Group> groupList = factory.getGroupDAO().getGroupByFlow(initalRecord.getGroup().getFlow());
		for (Group group : groupList) {
			Record thisGroupRecord = factory.getRecordDAO().getRecordForGroupLikeThis(group, initalRecord);
			if (thisGroupRecord != null) {
				cancellation.setRecord(thisGroupRecord);
				factory.getCancellationDAO().add(cancellation);
			}
		}
	}

	private void deleteFlowRecord(Record initalRecord) throws DAOException {
		DAOFactory factory = DAOFactory.getInstance();

		List<Group> groupList = factory.getGroupDAO().getGroupByFlow(initalRecord.getGroup().getFlow());
		for (Group group : groupList) {
			Record thisGroupRecord = factory.getRecordDAO().getRecordForGroupLikeThis(group, initalRecord);
			if (thisGroupRecord != null) {
				factory.getRecordDAO().delete(thisGroupRecord);
			}
		}
	}

	/**
	 * Method checks whether the passed instance of {@link Record} conflicts
	 * with similar records
	 * 
	 * @param record
	 * @throws ServiceException
	 * @throws ServiceValidationException
	 */
	private void checkRecordForConflict(Record record) throws ServiceException, ServiceValidationException {
		List<Record> recordList = getAllRecordListForGroup(record.getGroup(), record.getDateFrom(), record.getDateTo());

		for (Record elem : recordList) {
			if (elem.getIdRecord() != record.getIdRecord() && elem.getWeekNumber() == record.getWeekNumber()
					&& elem.getWeekDay() == record.getWeekDay()
					&& elem.getSubjOrdinalNumber() == record.getSubjOrdinalNumber()
					&& elem.getSubjectFor().equals(record.getSubjectFor())) {

				throw new ServiceValidationException("Запись конфликтует с другой!");
			}
		}

	}
}