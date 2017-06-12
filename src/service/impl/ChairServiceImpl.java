package service.impl;

import hibernateFiles.entity.Chair;
import java.util.List;

import dao.exception.DAOException;
import dao.factory.DAOFactory;
import service.IChairService;
import service.exception.ServiceException;

public class ChairServiceImpl implements IChairService {

	@Override
	public void addChair(Chair chair) throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();

		try {
			factory.getChairDAO().add(chair);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при вставке", e);
		}
	}

	@Override
	public void updateChair(Chair chair) throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();

		try {
			factory.getChairDAO().update(chair);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при обновлении", e);
		}
	}

	@Override
	public Chair getChairById(int chair_id) throws ServiceException {

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
	public List<Chair> getAllChairs() throws ServiceException {

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