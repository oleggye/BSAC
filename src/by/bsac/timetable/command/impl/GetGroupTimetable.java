package by.bsac.timetable.command.impl;

import java.util.Date;
import java.util.List;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.hibernateFiles.entity.Group;
import by.bsac.timetable.hibernateFiles.entity.Record;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.factory.IServiceFactory;
import by.bsac.timetable.service.factory.ServiceFactoryName;
import by.bsac.timetable.service.factory.ServiceFactoryProvider;
import supportClasses.DateUtil;

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