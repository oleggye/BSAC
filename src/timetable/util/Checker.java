package timetable.util;

import by.bsac.timetable.hibernateFiles.entity.Group;
import by.bsac.timetable.hibernateFiles.entity.Record;

public final class Checker {

	public static boolean isGroupInFlow(Group group) {
		if (group == null || group.getFlow() == null) {
			return false;
		}
		return true;
	}

	public static boolean isRecordForOneDate(Record record) {
		if (record == null || !record.getDateFrom().equals(record.getDateTo())) {
			return false;
		}
		return true;
	}

}