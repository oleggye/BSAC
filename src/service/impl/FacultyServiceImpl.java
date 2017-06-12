package service.impl;

import hibernateFiles.HibernateUtil;
import hibernateFiles.entity.Faculty;
import java.util.List;

import dao.exception.DAOException;
import dao.factory.DAOFactory;
import service.IFacultyService;
import service.exception.ServiceException;

public class FacultyServiceImpl implements IFacultyService {

	@Override
	public void addFaculties(Faculty faculty) throws ServiceException {

		DAOFactory factory = DAOFactory.getInstance();

		try {
			factory.getFacultyDAO().add(faculty);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при вставке", e);
		}
	}

	@Override
	public void updateFaculty(Faculty faculty) throws ServiceException {

		DAOFactory factory = DAOFactory.getInstance();

		try {
			factory.getFacultyDAO().update(faculty);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при обновлении факультета", e);
		}
	}

	@Override
	public Faculty getFacultyById(int faculty_id) throws ServiceException {

		Faculty faculty = null;
		DAOFactory factory = DAOFactory.getInstance();

		try {
			faculty = factory.getFacultyDAO().getById(faculty_id);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении факультета", e);
		}
		return faculty;
	}

	@Override
	public List<Faculty> getAllFaculties() throws ServiceException {

		List<Faculty> facultyList = null;
		DAOFactory factory = DAOFactory.getInstance();

		try {
			facultyList = factory.getFacultyDAO().getAll();
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении факультетов", e);
		}
		return facultyList;
	}

	@Override
	public void deleteFaculty(Faculty faculty) throws ServiceException {

		DAOFactory factory = DAOFactory.getInstance();

		try {
			factory.getFacultyDAO().delete(faculty);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при удалении группы", e);
		}
	}
}