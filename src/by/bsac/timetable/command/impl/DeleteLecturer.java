package by.bsac.timetable.command.impl;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.hibernateFiles.entity.Lecturer;
import by.bsac.timetable.service.ILecturerService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.factory.IServiceFactory;
import by.bsac.timetable.service.factory.ServiceFactoryProvider;

public class DeleteLecturer implements ICommand {

	@Override
	public void execute(Request request) throws CommandException {
		IServiceFactory serviceFactory = ServiceFactoryProvider.getInstance().getServiceFactory();
		ILecturerService service = serviceFactory.getLecturerService();
		Lecturer lecturer = (Lecturer) request.getValue("lecturer");

		try {
			service.deleteLecturer(lecturer);
		} catch (ServiceException e) {
			throw new CommandException(e);
		}
	}
}