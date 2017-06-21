package by.bsac.timetable.command.impl;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.hibernateFiles.entity.Flow;
import by.bsac.timetable.hibernateFiles.entity.Group;
import by.bsac.timetable.service.IGroupService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.service.factory.IServiceFactory;
import by.bsac.timetable.service.factory.ServiceFactoryProvider;

public class UpdateGroup implements ICommand {

	@Override
	public void execute(Request request) throws CommandException {

		IServiceFactory serviceFactory = ServiceFactoryProvider.getInstance().getServiceFactory();
		IGroupService service = serviceFactory.getGroupService();
		Group group = (Group) request.getValue("group");
		Flow groupFlow = group.getFlow();
		Flow newFlow = (Flow) request.getValue("flow");

		try {

			if (groupFlow != null && !groupFlow.equals(newFlow)) {
				// удалить все записи группы и задать новый поток
				service.changeGroupFlow(group, newFlow);
			} else {
				group.setFlow(newFlow);
				service.updateGroup(group);
			}
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}