/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.bsac.timetable.service.impl;

import java.util.List;

import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.dao.factory.DAOFactory;
import by.bsac.timetable.hibernateFiles.entity.Faculty;
import by.bsac.timetable.hibernateFiles.entity.Group;
import by.bsac.timetable.service.IGroupService;
import by.bsac.timetable.service.exception.ServiceException;

public class GroupServiceImpl implements IGroupService {

	@Override
	public void addGroup(Group group) throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();

		try {
			factory.getGroupDAO().add(group);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при вставке", e);
		}
	}

	@Override
	public void updateGroup(Group group) throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();

		try {
			factory.getGroupDAO().update(group);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при обновлении группы", e);
		}
	}

	@Override
	public Group getGroupById(int group_id) throws ServiceException {

		Group group = null;
		DAOFactory factory = DAOFactory.getInstance();

		try {
			group = factory.getGroupDAO().getById(group_id);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при поиске группы", e);
		}
		return group;
	}

	@Override
	public List<Group> getAllGroups() throws ServiceException {

		List<Group> groupList = null;
		DAOFactory factory = DAOFactory.getInstance();

		try {
			groupList = factory.getGroupDAO().getAll();
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении списка групп", e);
		}
		return groupList;
	}

	@Override
	public void deleteGroup(Group group) throws ServiceException {

		DAOFactory factory = DAOFactory.getInstance();

		try {
			factory.getGroupDAO().delete(group);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при удалении группы", e);
		}
	}

	@Override
	public List<Group> getGroupsRecordsByFacultyId(Faculty faculty) throws ServiceException {

		List<Group> groupList = null;
		DAOFactory factory = DAOFactory.getInstance();

		try {
			groupList = factory.getGroupDAO().getGroupListByFaculty(faculty);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении списка групп факультета", e);
		}
		return groupList;
	}

	@Override
	public List<Group> getGroupsRecordsByFacultyIdAndEduLevel(Faculty faculty, byte eduLevel) throws ServiceException {

		List<Group> groupList = null;
		DAOFactory factory = DAOFactory.getInstance();

		try {
			groupList = factory.getGroupDAO().getGroupListByFacultyAndEduLevel(faculty, eduLevel);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении списка групп факультета для уровня образования", e);
		}
		return groupList;
	}
}