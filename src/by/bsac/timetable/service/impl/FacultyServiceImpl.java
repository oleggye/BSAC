package by.bsac.timetable.service.impl;

import java.util.List;

import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.dao.factory.DAOFactory;
import by.bsac.timetable.hibernateFiles.entity.Faculty;
import by.bsac.timetable.service.IFacultyService;
import by.bsac.timetable.service.IValidationService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.service.factory.impl.ServiceFactory;

public class FacultyServiceImpl implements IFacultyService {

	@Override
	public void addFaculties(Faculty faculty) throws ServiceException, ServiceValidationException {

		IValidationService service = ServiceFactory.getInstance().getValidationService();
		service.validateFaculty(faculty);

		DAOFactory factory = DAOFactory.getInstance();

		try {
			factory.getFacultyDAO().add(faculty);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при вставке", e);
		}
	}

	@Override
	public void updateFaculty(Faculty faculty) throws ServiceException, ServiceValidationException {

		IValidationService service = ServiceFactory.getInstance().getValidationService();
		service.validateFaculty(faculty);

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