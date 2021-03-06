package by.bsac.timetable.dao;

import java.util.Date;
import java.util.List;

import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.hibernateFiles.entity.Group;
import by.bsac.timetable.hibernateFiles.entity.Record;
import by.bsac.timetable.hibernateFiles.entity.SubjectFor;

public interface IRecordDAO {

	public void add(Record mainRecord) throws DAOException;

	public void addAll(List<Record> listRecord) throws DAOException;

	public void update(Record mainRecord) throws DAOException;

	public void updateAll(List<Record> listRecord) throws DAOException;

	public Record getById(Number mainRecord_id) throws DAOException;

	public List<Record> getAll() throws DAOException;

	public void delete(Record mainRecord) throws DAOException;

	public List<Record> getRecordListByGroupAndDate(Group group, Date dateFrom, Date dateTo) throws DAOException;

	public Record getRecordForGroupLikeThis(Group group, Record record) throws DAOException;

	public List<Record> getRecordListByGroupAndSubjectFor(Group group, SubjectFor subjectFor) throws DAOException;
}