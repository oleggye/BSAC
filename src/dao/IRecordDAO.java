package dao;

import hibernateFiles.entity.Group;
import hibernateFiles.entity.Record;
import java.util.Date;
import java.util.List;

import dao.exception.DAOException;

public interface IRecordDAO {

	public void add(Record mainRecord) throws DAOException;

	public void update(Record mainRecord) throws DAOException;

	public Record getById(Number mainRecord_id) throws DAOException;

	public List<Record> getAll() throws DAOException;

	public void delete(Record mainRecord) throws DAOException;

	public List<Record> getRecordListByGroupAndDate(Group group, Date dateFrom, Date dateTo) throws DAOException;
}