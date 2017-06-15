package by.bsac.timetable.command.impl;

import java.util.List;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.hibernateFiles.entity.Flow;
import by.bsac.timetable.hibernateFiles.entity.Group;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.service.factory.IServiceFactory;
import by.bsac.timetable.service.factory.ServiceFactoryName;
import by.bsac.timetable.service.factory.ServiceFactoryProvider;

public class GetFlowGroupList implements ICommand {

	@Override
	public void execute(Request request) throws CommandException {
		IServiceFactory factory = ServiceFactoryProvider.getInstance().getServiceFactory(ServiceFactoryName.CHOKE);

		Flow flow = (Flow) request.getValue("flow");

		try {
			List<Group> groupList = factory.getGroupService().getGroupListByFlow(flow);
			request.putParam("groupList", groupList);
			
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}