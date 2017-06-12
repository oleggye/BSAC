package dao;

import hibernateFiles.entity.Chair;
import hibernateFiles.entity.Lecturer;
import java.util.List;

import dao.exception.DAOException;

public interface ILecturerDAO {

	public void add(Lecturer lecturer) throws DAOException;

	public void update(Lecturer lecturer) throws DAOException;

	public Lecturer getById(Number lecturer_id) throws DAOException;

	public List<Lecturer> getAll() throws DAOException;

	public void delete(Lecturer lecturer) throws DAOException;

	public List<Lecturer> getLecturerListByChair(Chair chair) throws DAOException;
}