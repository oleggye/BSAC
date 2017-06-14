package by.bsac.timetable.dao;

import java.util.List;

import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.hibernateFiles.entity.Classroom;

public interface IClassroomDAO {

	public void add(Classroom chair) throws DAOException;

	public void addAll(List<Classroom> listClassroom) throws DAOException;

	public void update(Classroom chair) throws DAOException;

	public void updateAll(List<Classroom> listClassroom) throws DAOException;

	public Classroom getById(Number chair_id) throws DAOException;

	public List<Classroom> getAll() throws DAOException;

	public void delete(Classroom chair) throws DAOException;
}