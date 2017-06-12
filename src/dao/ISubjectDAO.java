package dao;

import hibernateFiles.entity.Chair;
import hibernateFiles.entity.Subject;
import java.util.List;

import dao.exception.DAOException;

public interface ISubjectDAO {

	public void add(Subject subject) throws DAOException;

	public void update(Subject subject) throws DAOException;

	public Subject getById(Number subject_id) throws DAOException;

	public List<Subject> getAll() throws DAOException;

	public void delete(Subject subject) throws DAOException;

	public List<Subject> getSubjectListByChair(Chair chair) throws DAOException;

	public List<Subject> getSubjectListByChairAndEduLevel(Chair chair, byte eduLevel) throws DAOException;
}