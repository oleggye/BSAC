package by.bsac.timetable.service.impl;

import java.util.List;

import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.dao.factory.DAOFactory;
import by.bsac.timetable.hibernateFiles.entity.Faculty;
import by.bsac.timetable.hibernateFiles.entity.Flow;
import by.bsac.timetable.hibernateFiles.entity.Group;
import by.bsac.timetable.service.IGroupService;
import by.bsac.timetable.service.IValidationService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.service.factory.impl.ServiceFactory;

public class GroupServiceImpl implements IGroupService {

	@Override
	public void addGroup(Group group) throws ServiceException, ServiceValidationException {
		IValidationService service = ServiceFactory.getInstance().getValidationService();
		service.validateGroup(group);

		DAOFactory factory = DAOFactory.getInstance();

		try {
			factory.getGroupDAO().add(group);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при вставке", e);
		}
	}

	@Override
	public void updateGroup(Group group) throws ServiceException, ServiceValidationException {
		IValidationService service = ServiceFactory.getInstance().getValidationService();
		service.validateGroup(group);

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
	public List<Group> getGroupList() throws ServiceException {

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
	public List<Group> getGroupListByFaculty(Faculty faculty) throws ServiceException {

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
	public List<Group> getGroupListByFacultyAndEduLevel(Faculty faculty, byte eduLevel) throws ServiceException {

		List<Group> groupList = null;
		DAOFactory factory = DAOFactory.getInstance();

		try {
			groupList = factory.getGroupDAO().getGroupListByFacultyAndEduLevel(faculty, eduLevel);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении списка групп факультета для уровня образования", e);
		}
		return groupList;
	}

	@Override
	public List<Group> getGroupListByFlow(Flow flow) throws ServiceException, ServiceValidationException {
		IValidationService service = ServiceFactory.getInstance().getValidationService();
		service.validateFlow(flow);

		List<Group> groupList;
		DAOFactory factory = DAOFactory.getInstance();

		try {
			groupList = factory.getGroupDAO().getGroupListByFlow(flow);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении списка групп потока", e);
		}
		return groupList;
	}

	@Override
	public void changeGroupFlow(Group group, Flow newFlow) throws ServiceException, ServiceValidationException {
		IValidationService service = ServiceFactory.getInstance().getValidationService();
		service.validateGroup(group);
		Flow flow = group.getFlow();
		service.validateFlow(flow);

		DAOFactory factory = DAOFactory.getInstance();
		try {
			factory.getGroupDAO().changeGroupFlow(group, newFlow);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при обновлении потока группы", e);
		}
	}

	@Override
	public List<Group> getGroupListByName(String name) throws ServiceException {
		List<Group> groupList;
		DAOFactory factory = DAOFactory.getInstance();

		try {
			groupList = factory.getGroupDAO().getAllWithSimilarName(name);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении списка групп", e);
		}
		return groupList;
	}
}