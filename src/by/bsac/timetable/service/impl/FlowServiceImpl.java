package by.bsac.timetable.service.impl;

import java.util.List;

import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.dao.factory.DAOFactory;
import by.bsac.timetable.hibernateFiles.entity.Flow;
import by.bsac.timetable.service.IFlowService;
import by.bsac.timetable.service.IValidationService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.service.factory.impl.ServiceFactory;

public class FlowServiceImpl implements IFlowService {

	@Override
	public void addFlow(Flow flow) throws ServiceException, ServiceValidationException {
		IValidationService service = ServiceFactory.getInstance().getValidationService();
		service.validateFlow(flow);

		DAOFactory factory = DAOFactory.getInstance();

		try {
			factory.getFlowDAO().add(flow);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при добавлении потока", e);
		}
	}

	@Override
	public void updateFlow(Flow flow) throws ServiceException, ServiceValidationException {
		IValidationService service = ServiceFactory.getInstance().getValidationService();
		service.validateFlow(flow);

		DAOFactory factory = DAOFactory.getInstance();

		try {
			factory.getFlowDAO().update(flow);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при обновлении потока", e);
		}
	}

	@Override
	public Flow getFlowById(short idFlow) throws ServiceException, ServiceValidationException {

		DAOFactory factory = DAOFactory.getInstance();
		Flow flow;
		try {
			flow = factory.getFlowDAO().getById(idFlow);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении потока с id: " + idFlow, e);
		}
		return flow;
	}

	@Override
	public List<Flow> getFlowList() throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();
		List<Flow> flowList;
		try {
			flowList = factory.getFlowDAO().getAll();
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении всех потоков", e);
		}
		return flowList;
	}

	@Override
	public void deleteFlow(Flow flow) throws ServiceException, ServiceValidationException {

		DAOFactory factory = DAOFactory.getInstance();

		try {
			factory.getFlowDAO().delete(flow);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при удалении потока", e);
		}
	}

	@Override
	public List<Flow> getFlowListByName(String name) throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();
		List<Flow> flowList;
		try {
			flowList = factory.getFlowDAO().getAllWithSimilarName(name);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении потоков", e);
		}
		return flowList;
	}
}