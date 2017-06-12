package timetable.command.impl;

import hibernateFiles.entity.Record;
import service.IRecordService;
import service.exception.ServiceException;
import service.exception.ServiceValidationException;
import service.factory.IServiceFactory;
import service.factory.ServiceFactoryName;
import service.factory.ServiceFactoryProvider;
import timetable.command.ICommand;
import timetable.command.exception.CommandException;
import timetable.command.util.Request;

public class AddRecord implements ICommand {

	@Override
	public void execute(Request request) throws CommandException {
		IServiceFactory serviceFactory = ServiceFactoryProvider.getInstance()
				.getServiceFactory(ServiceFactoryName.CHOKE);
		IRecordService service = serviceFactory.getRecordService();
		Record record = (Record) request.getValue("addRecord");

		try {
			service.addRecord(record);
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}