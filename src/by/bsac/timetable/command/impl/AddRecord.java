package by.bsac.timetable.command.impl;

import java.util.Iterator;
import java.util.Set;

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
import timetable.util.WeekNumber;

public class AddRecord implements ICommand {

	@Override
	public void execute(Request request) throws CommandException {
		IServiceFactory serviceFactory = ServiceFactoryProvider.getInstance()
				.getServiceFactory(ServiceFactoryName.CHOKE);
		IRecordService service = serviceFactory.getRecordService();
		Record record = (Record) request.getValue("addRecord");
		Set<WeekNumber> weekSet = (Set<WeekNumber>) request.getValue("weekSet");

		try {
			Iterator<WeekNumber> it = weekSet.iterator();
			while (it.hasNext()) {
				WeekNumber weekNumber = it.next();
				byte weekNumberValue = weekNumber.getWeekNumber();
				record.setWeekNumber(weekNumberValue);
				service.addRecord(record);
			}
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}