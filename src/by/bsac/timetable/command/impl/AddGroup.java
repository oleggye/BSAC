package by.bsac.timetable.command.impl;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.hibernateFiles.entity.Group;
import by.bsac.timetable.service.IGroupService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.service.factory.IServiceFactory;
import by.bsac.timetable.service.factory.ServiceFactoryProvider;

public class AddGroup implements ICommand {

	@Override
	public void execute(Request request) throws CommandException {

		IServiceFactory serviceFactory = ServiceFactoryProvider.getInstance().getServiceFactory();
		IGroupService service = serviceFactory.getGroupService();
		Group group = (Group) request.getValue("group");

		try {
			service.addGroup(group);
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}