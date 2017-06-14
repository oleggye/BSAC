package by.bsac.timetable.command.impl;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.hibernateFiles.entity.Record;
import by.bsac.timetable.service.IRecordService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.service.factory.IServiceFactory;
import by.bsac.timetable.service.factory.ServiceFactoryName;
import by.bsac.timetable.service.factory.ServiceFactoryProvider;
import timetable.util.LessonPeriod;

public class CancelRecord implements ICommand {

	@Override
	public void execute(Request request) throws CommandException {

		IServiceFactory serviceFactory = ServiceFactoryProvider.getInstance()
				.getServiceFactory(ServiceFactoryName.CHOKE);
		IRecordService service = serviceFactory.getRecordService();
		Record cancelRecord = (Record) request.getValue("cancelRecord");
		LessonPeriod cancelLessonPeriod = (LessonPeriod) request.getValue("cancelLessonPeriod");
		Record initialRecord = (Record) request.getValue("initialRecord");

		try {
			service.cancelRecord(initialRecord, cancelRecord, cancelLessonPeriod);
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}