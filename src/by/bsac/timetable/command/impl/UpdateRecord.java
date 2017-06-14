package by.bsac.timetable.command.impl;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.hibernateFiles.entity.Record;
import by.bsac.timetable.service.IRecordService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.service.factory.IServiceFactory;
import by.bsac.timetable.service.factory.ServiceFactoryProvider;

public class UpdateRecord implements ICommand {

	@Override
	public void execute(Request request) throws CommandException {

		IServiceFactory serviceFactory = ServiceFactoryProvider.getInstance().getServiceFactory();
		IRecordService service = serviceFactory.getRecordService();
		Record updateRecord = (Record) request.getValue("updateRecord");
		Record initialRecord = (Record) request.getValue("initialRecord");

		try {
			service.updateRecord(initialRecord,updateRecord);
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}