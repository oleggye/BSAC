package service;

import java.util.Date;
import hibernateFiles.entity.Group;
import hibernateFiles.entity.Record;
import java.util.List;
import service.exception.ServiceException;
import service.exception.ServiceValidationException;

public interface IRecordService {

	public void addRecord(Record record) throws ServiceException, ServiceValidationException;

	public void updateRecord(Record record) throws ServiceException, ServiceValidationException;

	public Record getRecord(int id_main) throws ServiceException;

	public void cancelRecord(Record record) throws ServiceException;

	public void cancelPeriodicRecord(Record record) throws ServiceException;

	public void deleteRecord(Record record) throws ServiceException;

	public List<Record> getAllRecordListForGroup(Group group, Date dateFrom, Date dateTo) throws ServiceException;
}