package service.impl;

import java.time.LocalDate;
import java.util.List;

import dao.exception.DAOException;
import dao.factory.DAOFactory;
import hibernateFiles.entity.Classroom;
import hibernateFiles.entity.Group;
import service.IClassroomService;
import service.exception.ServiceException;

public class ClassroomServiceImpl implements IClassroomService {

	@Override
	public void addClassroom(Classroom classroom) throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();

		try {
			factory.getClassroomDAO().add(classroom);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при удалении аудиторий", e);
		}
	}

	@Override
	public void updateClassroom(Classroom classroom) throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();

		try {
			factory.getClassroomDAO().update(classroom);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при удалении аудиторий", e);
		}
	}

	@Override
	public Classroom getClassroom(int idClassroom) throws ServiceException {

		Classroom classroom = null;
		DAOFactory factory = DAOFactory.getInstance();

		try {
			classroom = factory.getClassroomDAO().getById(idClassroom);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении аудитории", e);
		}
		return classroom;
	}

	@Override
	public void deleteClassroom(Classroom classroom) throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();

		try {
			factory.getClassroomDAO().delete(classroom);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при удалении аудиторий", e);
		}
	}

	@Override
	public List<Classroom> getClassroomListForGroup(Group group, LocalDate dateFrom, LocalDate dateTo)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Classroom> getClassroomList() throws ServiceException {

		List<Classroom> classroomList = null;
		DAOFactory factory = DAOFactory.getInstance();

		try {
			classroomList = factory.getClassroomDAO().getAll();
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении аудиторий", e);
		}
		return classroomList;
	}

}