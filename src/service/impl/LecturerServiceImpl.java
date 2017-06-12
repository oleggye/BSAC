/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.impl;

import hibernateFiles.entity.Chair;
import hibernateFiles.entity.Lecturer;
import java.util.List;

import dao.exception.DAOException;
import dao.factory.DAOFactory;
import service.ILecturerService;
import service.exception.ServiceException;

public class LecturerServiceImpl implements ILecturerService {

	@Override
	public void addLecturer(Lecturer lecturer) throws ServiceException {

		DAOFactory factory = DAOFactory.getInstance();

		try {
			factory.getLecturerDAO().add(lecturer);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при вставке", e);
		}
	}

	@Override
	public void updateLecturer(Lecturer lecturer) throws ServiceException {

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
}