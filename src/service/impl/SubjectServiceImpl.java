/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.impl;

import hibernateFiles.entity.Chair;
import hibernateFiles.entity.Subject;
import java.util.List;

import dao.exception.DAOException;
import dao.factory.DAOFactory;
import service.ISubjectService;
import service.exception.ServiceException;

public class SubjectServiceImpl implements ISubjectService {

	@Override
	public void addSubject(Subject subject) throws ServiceException {

		DAOFactory factory = DAOFactory.getInstance();

		try {
			factory.getSubjectDAO().add(subject);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при вставке", e);
		}
	}

	@Override
	public void updateSubject(Subject subject) throws ServiceException {

		DAOFactory factory = DAOFactory.getInstance();

		try {
			factory.getSubjectDAO().update(subject);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при обновлении", e);
		}
	}

	@Override
	public Subject getSubjectById(int subject_id) throws ServiceException {

		Subject subject = null;
		DAOFactory factory = DAOFactory.getInstance();

		try {
			subject = factory.getSubjectDAO().getById(subject_id);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении предмета", e);
		}
		return subject;
	}

	@Override
	public List<Subject> getAllSubjects() throws ServiceException {

		List<Subject> subjectList = null;
		DAOFactory factory = DAOFactory.getInstance();

		try {
			subjectList = factory.getSubjectDAO().getAll();
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении предметов", e);
		}
		return subjectList;
	}

	@Override
	public void deleteSubject(Subject subject) throws ServiceException {

		DAOFactory factory = DAOFactory.getInstance();

		try {
			factory.getSubjectDAO().delete(subject);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при удалении", e);
		}
	}

	@Override
	public List<Subject> getSubjectsRecordsByChairId(Chair chair) throws ServiceException {

		List<Subject> subjectList = null;
		DAOFactory factory = DAOFactory.getInstance();

		try {
			subjectList = factory.getSubjectDAO().getSubjectListByChair(chair);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении предметов кафедры", e);
		}
		return subjectList;
	}

	@Override
	public List<Subject> getSubjectsRecordsByChairIdAndEduLevel(Chair chair, byte eduLevel) throws ServiceException {

		List<Subject> subjectList = null;
		DAOFactory factory = DAOFactory.getInstance();

		try {
			subjectList = factory.getSubjectDAO().getSubjectListByChairAndEduLevel(chair, eduLevel);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении предметов кафедры для уровня образования", e);
		}
		return subjectList;
	}
}