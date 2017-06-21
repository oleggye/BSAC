package by.bsac.timetable.service;

import java.util.Date;
import java.util.List;

import by.bsac.timetable.hibernateFiles.entity.Group;
import by.bsac.timetable.hibernateFiles.entity.Record;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.util.LessonPeriod;

public interface IRecordService {

	public void addRecord(Record record) throws ServiceException, ServiceValidationException;

	public void updateRecord(Record initialRecord, Record updateRecord)
			throws ServiceException, ServiceValidationException;

	public Record getRecord(int id_main) throws ServiceException;

	public void cancelRecord(Record initialRecord, Record cancelRecord, LessonPeriod cancelLessonPeriod)
			throws ServiceException, ServiceValidationException;

	public void deleteRecord(Record record) throws ServiceException;

	public List<Record> getAllRecordListForGroup(Group group, Date dateFrom, Date dateTo) throws ServiceException;
}