package by.bsac.timetable.service.impl.choke;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import by.bsac.timetable.hibernateFiles.entity.Classroom;
import by.bsac.timetable.hibernateFiles.entity.Group;
import by.bsac.timetable.hibernateFiles.entity.Lecturer;
import by.bsac.timetable.hibernateFiles.entity.Record;
import by.bsac.timetable.hibernateFiles.entity.Subject;
import by.bsac.timetable.hibernateFiles.entity.SubjectFor;
import by.bsac.timetable.hibernateFiles.entity.SubjectType;
import by.bsac.timetable.hibernateFiles.entity.builder.RecordBuilder;
import by.bsac.timetable.hibernateFiles.entity.builder.SubjectForBuilder;
import by.bsac.timetable.hibernateFiles.entity.builder.SubjectTypeBuilder;
import by.bsac.timetable.service.IRecordService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.util.LessonPeriod;
import supportClasses.DateUtil;

public class RecordServiceChoke implements IRecordService {

	private static List<Record> recordList = new ArrayList<>();

	static {
		try {
			LecturerServiceChoke lecturerChoke = new LecturerServiceChoke();
			SubjectServiceChoke subjectChoke = new SubjectServiceChoke();
			GroupServiceChoke groupChoke = new GroupServiceChoke();
			ClassroomServiceChoke classroomChoke = new ClassroomServiceChoke();

			SubjectFor subjectFor1 = new SubjectForBuilder().buildId((byte) 1).build();
			SubjectFor subjectFor2 = new SubjectForBuilder().buildId((byte) 2).build();
			SubjectFor subjectFor3 = new SubjectForBuilder().buildId((byte) 3).build();

			Subject subject1 = subjectChoke.getSubjectById(1);
			Subject subject2 = subjectChoke.getSubjectById(2);
			Subject subject3 = subjectChoke.getSubjectById(3);

			Lecturer lecturer1 = lecturerChoke.getLecturerById(1);
			Lecturer lecturer2 = lecturerChoke.getLecturerById(2);
			Lecturer lecturer3 = lecturerChoke.getLecturerById(3);

			SubjectType subjectType1 = new SubjectTypeBuilder().buildId((byte) 1).build();
			SubjectType subjectType2 = new SubjectTypeBuilder().buildId((byte) 2).build();
			SubjectType subjectType3 = new SubjectTypeBuilder().buildId((byte) 3).build();

			Group group1 = groupChoke.getGroupById(1);
			Group group2 = groupChoke.getGroupById(2);
			Group group3 = groupChoke.getGroupById(3);

			Classroom room1 = classroomChoke.getClassroom(1);
			Classroom room2 = classroomChoke.getClassroom(2);
			Classroom room3 = classroomChoke.getClassroom(3);

			Record record1 = new RecordBuilder().buildWeekNumber((byte) 1).buildWeekDay((byte) 1)
					.buildLecturer(lecturer1).buildSubjOrdinalNumber((byte) 1).buildSubjectFor(subjectFor1)
					.buildSubject(subject1).buildSubjectType(subjectType3).buildGroup(group1).buildClassroom(room1)
					.build();

			Record record2 = new RecordBuilder().buildWeekNumber((byte) 1).buildWeekDay((byte) 1)
					.buildLecturer(lecturer2).buildSubjOrdinalNumber((byte) 1).buildSubjectFor(subjectFor2)
					.buildSubject(subject2).buildSubjectType(subjectType3).buildGroup(group2).buildClassroom(room2)
					.build();

			Record record3 = new RecordBuilder().buildWeekNumber((byte) 3).buildWeekDay((byte) 1)
					.buildLecturer(lecturer3).buildSubjOrdinalNumber((byte) 2).buildSubjectFor(subjectFor3)
					.buildSubject(subject3).buildSubjectType(subjectType2).buildGroup(group3).buildClassroom(room3)
					.build();

			Record record4 = new RecordBuilder().buildWeekNumber((byte) 4).buildWeekDay((byte) 3)
					.buildLecturer(lecturer3).buildSubjOrdinalNumber((byte) 1).buildSubjectFor(subjectFor3)
					.buildSubject(subject3).buildSubjectType(subjectType1).buildGroup(group3).buildClassroom(room3)
					.build();

			recordList.add(record1);
			recordList.add(record2);
			recordList.add(record3);
			recordList.add(record4);
			// recordList = Arrays.asList(record1, record2, record3, record4);

		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addRecord(Record record) throws ServiceException {
		/*
		 * Если это пара на одну дату, то задаем номер и день недели согласно
		 * этой дате
		 */
		Date dateFrom = record.getDateFrom();
		Date dateTo = record.getDateTo();
		if (dateFrom.equals(dateTo)) {
			byte weekNumber = DateUtil.getWeekNumber(dateFrom);
			byte weekDay = DateUtil.getWeekDay(dateFrom);
			record.setWeekNumber(weekNumber);
			record.setWeekDay(weekDay);
		}
		System.out.println("AddRecord:" + record.printRecord());
		recordList.add(record);

	}

	@Override
	public void updateRecord(Record initialRecord, Record updateRecord)
			throws ServiceException, ServiceValidationException {
		System.out.println("UpdateRecord:" + updateRecord.printRecord());
		for (int index = 0; index < recordList.size(); index++) {
			if (updateRecord.getIdRecord() == recordList.get(index).getIdRecord()) {
				recordList.set(index, updateRecord);
			}
		}

	}

	@Override
	public Record getRecord(int id_main) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRecord(Record mainRecord) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Record> getAllRecordListForGroup(Group group, Date dateFrom, Date dateTo) throws ServiceException {
		ListIterator<Record> iterator = recordList.listIterator();
		while (iterator.hasNext()) {
			Record record = iterator.next();
			record.setGroup(group);
			if (record.getDateFrom() == null) {
				record.setDateFrom(dateFrom);
			}
			String string = "August 2, 2017";
			DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
			Date date = null;
			try {
				date = format.parse(string);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (record.getDateTo() == null) {
				record.setDateTo(date);
			}
		}
		return recordList;
	}

	@Override
	public void cancelRecord(Record record, Record cancelRecord, LessonPeriod cancelLessonPeriod)
			throws ServiceException, ServiceValidationException {
		System.out.println("CancelRecord:" + record);

	}
}