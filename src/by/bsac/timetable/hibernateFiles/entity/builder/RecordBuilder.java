package by.bsac.timetable.hibernateFiles.entity.builder;

import java.util.Date;

import by.bsac.timetable.hibernateFiles.entity.Classroom;
import by.bsac.timetable.hibernateFiles.entity.Group;
import by.bsac.timetable.hibernateFiles.entity.Lecturer;
import by.bsac.timetable.hibernateFiles.entity.Record;
import by.bsac.timetable.hibernateFiles.entity.Subject;
import by.bsac.timetable.hibernateFiles.entity.SubjectFor;
import by.bsac.timetable.hibernateFiles.entity.SubjectType;

public class RecordBuilder {

	private int idRecord;
	private Classroom classroom;
	private Group group;
	private Lecturer lecturer;
	private Subject subject;
	private SubjectFor subjectFor;
	private SubjectType subjectType;
	private byte weekNumber;
	private byte weekDay;
	private byte subjOrdinalNumber;
	private Date dateFrom;
	private Date dateTo;

	public RecordBuilder buildId(int idRecord) {
		this.idRecord = idRecord;
		return this;
	}

	public RecordBuilder buildClassroom(Classroom classroom) {
		this.classroom = classroom;
		return this;
	}

	public RecordBuilder buildGroup(Group group) {
		this.group = group;
		return this;
	}

	public RecordBuilder buildLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
		return this;
	}

	public RecordBuilder buildSubject(Subject subject) {
		this.subject = subject;
		return this;
	}

	public RecordBuilder buildSubjectFor(SubjectFor subjectFor) {
		this.subjectFor = subjectFor;
		return this;
	}

	public RecordBuilder buildSubjectType(SubjectType subjectType) {
		this.subjectType = subjectType;
		return this;
	}

	public RecordBuilder buildWeekNumber(byte weekNumber) {
		this.weekNumber = weekNumber;
		return this;
	}

	public RecordBuilder buildWeekDay(byte weekDay) {
		this.weekDay = weekDay;
		return this;
	}

	public RecordBuilder buildSubjOrdinalNumber(byte subjOrdinalNumber) {
		this.subjOrdinalNumber = subjOrdinalNumber;
		return this;
	}

	public RecordBuilder buildDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
		return this;
	}

	public RecordBuilder buildDateTo(Date dateTo) {
		this.dateTo = dateTo;
		return this;
	}

	public Record build() {
		Record record = new Record();
		record.setIdRecord(idRecord);
		record.setClassroom(classroom);
		record.setGroup(group);
		record.setLecturer(lecturer);
		record.setSubject(subject);
		record.setSubjectFor(subjectFor);
		record.setSubjectType(subjectType);
		record.setWeekNumber(weekNumber);
		record.setWeekDay(weekDay);
		record.setSubjOrdinalNumber(subjOrdinalNumber);
		record.setDateTo(dateTo);
		record.setDateFrom(dateFrom);
		return record;
	}
}