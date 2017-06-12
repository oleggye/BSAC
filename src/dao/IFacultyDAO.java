package dao;

import hibernateFiles.entity.Faculty;
import java.util.List;

import dao.exception.DAOException;

public interface IFacultyDAO {

	public void add(Faculty faculty) throws DAOException;

	public void update(Faculty faculty) throws DAOException;

	public Faculty getById(Number faculty_id) throws DAOException;

	public List<Faculty> getAll() throws DAOException;

	public void delete(Faculty faculty) throws DAOException;
}