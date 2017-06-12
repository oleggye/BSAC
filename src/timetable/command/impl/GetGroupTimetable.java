package timetable.command.impl;

import java.util.Date;
import java.util.List;

import hibernateFiles.entity.Group;
import hibernateFiles.entity.Record;
import service.exception.ServiceException;
import service.factory.IServiceFactory;
import service.factory.ServiceFactoryName;
import service.factory.ServiceFactoryProvider;
import supportClasses.DateUtil;
import timetable.command.ICommand;
import timetable.command.exception.CommandException;
import timetable.command.util.Request;

public class GetGroupTimetable implements ICommand {

	@Override
	public void execute(Request request) throws CommandException {

		IServiceFactory factory = ServiceFactoryProvider.getInstance().getServiceFactory(ServiceFactoryName.CHOKE);

		Group group = (Group) request.getValue("group");
		Date referenceDate = (Date) request.getValue("referenceDate");

		Date[] dateArr = DateUtil.getDateFromAndDateToByReferenceDate(referenceDate);
		Date dateFrom = dateArr[0];
		Date dateTo = dateArr[1];

		try {
			List<Record> recordList = factory.getRecordService().getAllRecordListForGroup(group, dateFrom, dateTo);

			request.putParam("recordList", recordList);
		} catch (ServiceException e) {
			throw new CommandException(e);
		}
	}
}