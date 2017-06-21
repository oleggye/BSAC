package by.bsac.timetable.service.impl;

import java.util.List;

import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.dao.factory.DAOFactory;
import by.bsac.timetable.hibernateFiles.entity.Chair;
import by.bsac.timetable.hibernateFiles.entity.Lecturer;
import by.bsac.timetable.service.ILecturerService;
import by.bsac.timetable.service.IValidationService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.service.factory.impl.ServiceFactory;

public class LecturerServiceImpl implements ILecturerService {

	@Override
	public void addLecturer(Lecturer lecturer) throws ServiceException, ServiceValidationException {
		IValidationService service = ServiceFactory.getInstance().getValidationService();
		service.validateLecturer(lecturer);

		DAOFactory factory = DAOFactory.getInstance();

		try {
			factory.getLecturerDAO().add(lecturer);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при вставке", e);
		}
	}

	@Override
	public void updateLecturer(Lecturer lecturer) throws ServiceException, ServiceValidationException {
		IValidationService service = ServiceFactory.getInstance().getValidationService();
		service.validateLecturer(lecturer);

		DAOFactory factory = DAOFactory.getInstance();

		try {
			factory.getLecturerDAO().update(lecturer);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при обновлении", e);
		}
	}

	@Override
	public Lecturer getLecturerById(int lecturer_id) throws ServiceException {

		Lecturer lecturer = null;
		DAOFactory factory = DAOFactory.getInstance();

		try {
			lecturer = factory.getLecturerDAO().getById(lecturer_id);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении преподавателя", e);
		}
		return lecturer;
	}

	@Override
	public List<Lecturer> getAllLecturers() throws ServiceException {

		List<Lecturer> lecturerList = null;
		DAOFactory factory = DAOFactory.getInstance();

		try {
			lecturerList = factory.getLecturerDAO().getAll();
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении преподавателей", e);
		}
		return lecturerList;
	}

	@Override
	public void deleteLecturer(Lecturer lecturer) throws ServiceException {

		DAOFactory factory = DAOFactory.getInstance();

		try {
			factory.getLecturerDAO().delete(lecturer);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при удалении", e);
		}
	}

	@Override
	public List<Lecturer> getLecturersRecordsByChairId(Chair chair) throws ServiceException {

		List<Lecturer> lecturerList = null;
		DAOFactory factory = DAOFactory.getInstance();

		try {
			lecturerList = factory.getLecturerDAO().getLecturerListByChair(chair);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении преподавателей", e);
		}
		return lecturerList;
	}

	@Override
	public List<Lecturer> getLecturerListByName(String nameLecturer)
			throws ServiceException, ServiceValidationException {

		List<Lecturer> lecturerList = null;
		DAOFactory factory = DAOFactory.getInstance();

		try {
			lecturerList = factory.getLecturerDAO().getAllWithSimilarName(nameLecturer);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении преподавателей", e);
		}
		return lecturerList;
	}
}