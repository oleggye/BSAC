package by.bsac.timetable.service.impl;

import java.util.List;

import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.dao.factory.DAOFactory;
import by.bsac.timetable.hibernateFiles.entity.Chair;
import by.bsac.timetable.service.IChairService;
import by.bsac.timetable.service.IValidationService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.service.factory.impl.ServiceFactory;

public class ChairServiceImpl implements IChairService {

	@Override
	public void addChair(Chair chair) throws ServiceException, ServiceValidationException {
		IValidationService service = ServiceFactory.getInstance().getValidationService();
		service.validateChair(chair);

		DAOFactory factory = DAOFactory.getInstance();

		try {
			factory.getChairDAO().add(chair);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при вставке", e);
		}
	}

	@Override
	public void updateChair(Chair chair) throws ServiceException, ServiceValidationException {
		IValidationService service = ServiceFactory.getInstance().getValidationService();
		service.validateChair(chair);

		DAOFactory factory = DAOFactory.getInstance();

		try {
			factory.getChairDAO().update(chair);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при обновлении", e);
		}
	}

	@Override
	public Chair getChairById(byte chair_id) throws ServiceException {

		Chair chair = null;
		DAOFactory factory = DAOFactory.getInstance();

		try {
			chair = factory.getChairDAO().getById(chair_id);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при поиске кафедры", e);
		}
		return chair;
	}

	@Override
	public List<Chair> getAllChair() throws ServiceException {

		List<Chair> chairList = null;
		DAOFactory factory = DAOFactory.getInstance();

		try {

			chairList = factory.getChairDAO().getAll();
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении списка кафедр", e);
		}
		return chairList;
	}

	@Override
	public void deleteChair(Chair chair) throws ServiceException {

		DAOFactory factory = DAOFactory.getInstance();

		try {
			factory.getChairDAO().delete(chair);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при удалении", e);
		}
	}
}