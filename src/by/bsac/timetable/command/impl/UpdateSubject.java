package by.bsac.timetable.command.impl;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.hibernateFiles.entity.Subject;
import by.bsac.timetable.service.ISubjectService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.service.factory.IServiceFactory;
import by.bsac.timetable.service.factory.ServiceFactoryProvider;

public class UpdateSubject implements ICommand {

	@Override
	public void execute(Request request) throws CommandException {
		IServiceFactory serviceFactory = ServiceFactoryProvider.getInstance().getServiceFactory();
		ISubjectService service = serviceFactory.getSubjectService();
		Subject subject = (Subject) request.getValue("subject");

		try {
			service.updateSubject(subject);
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}