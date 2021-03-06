package by.bsac.timetable.dao;

import java.util.List;

import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.hibernateFiles.entity.Faculty;

public interface IFacultyDAO {

	public void add(Faculty faculty) throws DAOException;

	public void addAll(List<Faculty> listFaculty) throws DAOException;

	public void update(Faculty faculty) throws DAOException;

	public void updateAll(List<Faculty> listFaculty) throws DAOException;

	public Faculty getById(Number faculty_id) throws DAOException;

	public List<Faculty> getAll() throws DAOException;

	public void delete(Faculty faculty) throws DAOException;
}