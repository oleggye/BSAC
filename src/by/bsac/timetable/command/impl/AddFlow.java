package by.bsac.timetable.command.impl;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.hibernateFiles.entity.Flow;
import by.bsac.timetable.service.IFlowService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.service.factory.IServiceFactory;
import by.bsac.timetable.service.factory.ServiceFactoryProvider;

public class AddFlow implements ICommand {

	@Override
	public void execute(Request request) throws CommandException {
		IServiceFactory serviceFactory = ServiceFactoryProvider.getInstance().getServiceFactory();
		IFlowService service = serviceFactory.getFlowService();
		Flow flow = (Flow) request.getValue("flow");

		try {
			service.addFlow(flow);
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}