package dao;

import java.util.List;

import dao.exception.DAOException;
import hibernateFiles.entity.Classroom;

public interface IClassroomDAO {

	public void add(Classroom chair) throws DAOException;

	public void update(Classroom chair) throws DAOException;

	public Classroom getById(Number chair_id) throws DAOException;

	public List<Classroom> getAll() throws DAOException;

	public void delete(Classroom chair) throws DAOException;
}
