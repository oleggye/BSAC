/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import dao.exception.DAOException;
import dao.factory.DAOFactory;
import hibernateFiles.HibernateUtil;
import hibernateFiles.entity.Cancellation;
import hibernateFiles.entity.Group;
import hibernateFiles.entity.Record;
import service.IRecordService;
import service.IValidationService;
import service.exception.ServiceException;
import service.exception.ServiceValidationException;
import service.factory.impl.ServiceFactory;
import supportClasses.DateUtil;
import timetable.util.LessonFor;

public class RecordServiceImpl implements IRecordService {

	@Override
	public void addRecord(Record record) throws ServiceException, ServiceValidationException {

		IValidationService validationService = ServiceFactory.getInstance().getValidationService();
		validationService.validateRecord(record);

		DAOFactory factory = DAOFactory.getInstance();
		LessonFor lessonFor = LessonFor.subjectForToLessonFor(record.getSubjectFor());
		try {
			if (lessonFor.equals(LessonFor.FULL_FLOW)) {
				addRecordToFlow(record);
			} else {
				factory.getRecordDAO().add(record);
			}
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при вставке", e);
		}
	}

	@Override
	public void updateRecord(Record record) throws ServiceException, ServiceValidationException {

		IValidationService validationService = ServiceFactory.getInstance().getValidationService();
		validationService.validateRecord(record);

		/* если это пара на одну дату */
		if (record.getDateFrom().equals(record.getDateTo())) {
			byte weekDay = DateUtil.getWeekDay(record.getDateFrom());
			byte weekNumber = DateUtil.getWeekNumber(record.getDateFrom());
			record.setWeekDay(weekDay);
			record.setWeekNumber(weekNumber);
		}

		DAOFactory factory = DAOFactory.getInstance();
		try {
			factory.getRecordDAO().update(record);
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
	public void cancelRecord(Record record) throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();
		try {
			if (record.getDateFrom().equals(record.getDateTo())) {
				factory.getRecordDAO().delete(record);
			} else {
				Cancellation cancellation = new Cancellation();
				cancellation.setDateFrom(record.getDateFrom());
				cancellation.setDateTo(record.getDateTo());
				cancellation.setRecord(record);
				factory.getCancellationDAO().add(cancellation);
			}
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении занятия", e);
		}
	}

	@Override
	public void cancelPeriodicRecord(Record record) throws ServiceException {
		// TODO Auto-generated method stub
	}

	private void addRecordToFlow(Record record) throws DAOException {
		DAOFactory factory = DAOFactory.getInstance();

		List<Group> groupList = factory.getGroupDAO().getGroupByFlow(record.getGroup().getFlow());
		for (Group group : groupList) {
			record.setGroup(group);
			factory.getRecordDAO().add(record);
		}
	}
}