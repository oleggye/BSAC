package by.bsac.timetable.command.impl;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.hibernateFiles.entity.Chair;
import by.bsac.timetable.service.IChairService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.service.factory.IServiceFactory;
import by.bsac.timetable.service.factory.ServiceFactoryProvider;

public class AddChair implements ICommand {

	@Override
	public void execute(Request request) throws CommandException {

		IServiceFactory serviceFactory = ServiceFactoryProvider.getInstance().getServiceFactory();
		IChairService service = serviceFactory.getChairService();
		Chair chair = (Chair) request.getValue("chair");

		try {
			service.addChair(chair);
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}