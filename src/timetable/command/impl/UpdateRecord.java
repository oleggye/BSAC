package timetable.command.impl;

import hibernateFiles.entity.Record;
import service.IRecordService;
import service.exception.ServiceException;
import service.exception.ServiceValidationException;
import service.factory.IServiceFactory;
import service.factory.ServiceFactoryProvider;
import timetable.command.ICommand;
import timetable.command.exception.CommandException;
import timetable.command.util.Request;

public class UpdateRecord implements ICommand {

	@Override
	public void execute(Request request) throws CommandException {

		IServiceFactory serviceFactory = ServiceFactoryProvider.getInstance().getServiceFactory();
		IRecordService service = serviceFactory.getRecordService();
		Record record = (Record) request.getValue("updateRecord");

		try {
			service.updateRecord(record);
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}