package by.bsac.timetable.service.impl;

import java.util.Date;

import by.bsac.timetable.hibernateFiles.entity.Chair;
import by.bsac.timetable.hibernateFiles.entity.Classroom;
import by.bsac.timetable.hibernateFiles.entity.Faculty;
import by.bsac.timetable.hibernateFiles.entity.Flow;
import by.bsac.timetable.hibernateFiles.entity.Group;
import by.bsac.timetable.hibernateFiles.entity.Lecturer;
import by.bsac.timetable.hibernateFiles.entity.Record;
import by.bsac.timetable.hibernateFiles.entity.Subject;
import by.bsac.timetable.hibernateFiles.entity.SubjectFor;
import by.bsac.timetable.hibernateFiles.entity.SubjectType;
import by.bsac.timetable.service.IValidationService;
import by.bsac.timetable.service.exception.ServiceValidationException;

public class ValidationServiceImpl implements IValidationService {

	@Override
	public void validateRecord(Record record) throws ServiceValidationException {

		if (Checker.isDatesInvalid(record.getDateFrom(), record.getDateTo())) {
			throw new ServiceValidationException("Параметры даты введены не верно!");
		}
		Classroom classroom = record.getClassroom();
		if (Checker.isNull(classroom) || Checker.isIdInvalid(classroom.getIdClassroom())) {
			throw new ServiceValidationException("Не задана аудитория!");
		}
		Group group = record.getGroup();
		if (Checker.isNull(group) || Checker.isIdInvalid(group.getIdGroup())) {
			throw new ServiceValidationException("Не задана группа!");
		}
		Lecturer lecturer = record.getLecturer();
		if (Checker.isNull(lecturer) || Checker.isIdInvalid(lecturer.getIdLecturer())) {
			throw new ServiceValidationException("Не задан преподаватель!");
		}
		Subject subject = record.getSubject();
		if (Checker.isNull(subject) || Checker.isIdInvalid(subject.getIdSubject())) {
			throw new ServiceValidationException("Не задана дисциплина!");
		}
		SubjectFor subjectFor = record.getSubjectFor();
		if (Checker.isNull(subjectFor) || Checker.isIdInvalid(subjectFor.getId())) {
			throw new ServiceValidationException("Не задано, для кого назначается запись!");
		}
		SubjectType subjectType = record.getSubjectType();
		if (Checker.isNull(subjectType) || Checker.isIdInvalid(subjectType.getId())) {
			throw new ServiceValidationException("Не задан тип записи!");
		}
		byte weekNumber = record.getWeekNumber();
		if (Checker.isWeekNumberInvalid(weekNumber)) {
			throw new ServiceValidationException("Неверный номер недели: !" + weekNumber);
		}
		byte weekDay = record.getWeekDay();
		if (Checker.isWeekDayInvalid(weekDay)) {
			throw new ServiceValidationException("Неверный номер дня недели: !" + weekDay);
		}
		byte subjectOrdinalNumber = record.getSubjOrdinalNumber();
		if (Checker.isSubjectOrdinalNumberInvalid(subjectOrdinalNumber)) {
			throw new ServiceValidationException("Неверный порядковый номер занятия: !" + subjectOrdinalNumber);
		}

	}

	@Override
	public void validateFaculty(Faculty faculty) throws ServiceValidationException {
		String name = faculty.getNameFaculty();
		if (Checker.isNameInvalid(name)) {
			throw new ServiceValidationException("Неверно заданно название факультета: " + name);
		}
	}

	@Override
	public void validateChair(Chair chair) throws ServiceValidationException {
		String name = chair.getNameChair();
		if (Checker.isNameInvalid(name)) {
			throw new ServiceValidationException("Неверно заданно название кафедры: " + name);
		}
	}

	@Override
	public void validateClassroom(Classroom classroom) throws ServiceValidationException {
		short number = classroom.getNumber();
		if (Checker.isNumberInvalid(number)) {
			throw new ServiceValidationException("Неверно задан номер аудитории: " + number);
		}
		byte building = classroom.getBuilding();
		if (Checker.isBuildingInvalid(building)) {
			throw new ServiceValidationException("Неверно задан номер корпуса: " + number);
		}
	}

	@Override
	public void validateSubject(Subject subject) throws ServiceValidationException {
		String name = subject.getNameSubject();
		if (Checker.isNameInvalid(name)) {
			throw new ServiceValidationException("Неверное название предмета:" + name);
		}
		String abreviationName = subject.getAbnameSubject();
		if (Checker.isNameInvalid(abreviationName)) {
			throw new ServiceValidationException("Неверная аббревиатура:" + abreviationName);
		}
		byte educationLevel = subject.getEduLevel();
		if (Checker.isEducationLevelInvalid(educationLevel)) {
			throw new ServiceValidationException("Задан неверный уровень образования:" + educationLevel);
		}
	}

	@Override
	public void validateLecturer(Lecturer lecturer) throws ServiceValidationException {
		String name = lecturer.getNameLecturer();
		if (Checker.isNameInvalid(name)) {
			throw new ServiceValidationException("Неверное имя преподавателя:" + name);
		}
		Chair chair = lecturer.getChair();
		validateChair(chair);
	}

	@Override
	public void validateGroup(Group group) throws ServiceValidationException {
		String name = group.getNameGroup();
		if (Checker.isNameInvalid(name)) {
			throw new ServiceValidationException("Неверное название группы:" + name);
		}
		byte educationLevel = group.getEduLevel();
		if (Checker.isEducationLevelInvalid(educationLevel)) {
			throw new ServiceValidationException("Задан неверный уровень образования:" + educationLevel);
		}
		Faculty faculty = group.getFaculty();
		validateFaculty(faculty);
		Flow flow = group.getFlow();
		validateFlow(flow);
	}

	@Override
	public void validateFlow(Flow flow) throws ServiceValidationException {
		String name = flow.getName();
		if (Checker.isNameInvalid(name)) {
			throw new ServiceValidationException("Неверное название потока:" + name);
		}
	}

	private static class Checker {

		private static final short LOW_BUILDIN_NUMBER_BOUND = 0;
		private static final short TOP_BUILDIN_NUMBER_BOUND = 2;

		private static final short LOW_CLASSROOM_NUMBER_BOUND = 0;
		private static final short TOP_CLASSROOM_NUMBER_BOUND = 500;

		private static final short LOW_EDUCATION_LEVEL_BOUND = 1;
		private static final short TOP_EDUCATION_LEVEL_BOUND = 2;

		static boolean isDatesInvalid(Date dateFrom, Date dateTo) {

			if (isNull(dateFrom) || isNull(dateTo) || dateFrom.compareTo(dateTo) > 0) {
				return true;
			}
			return false;
		}

		static boolean isWeekNumberInvalid(byte weekNumber) {
			if (weekNumber < 1 || weekNumber > 4)
				return true;
			else
				return false;
		}

		static boolean isWeekDayInvalid(byte weekDay) {
			if (weekDay < 1 || weekDay > 7)
				return true;
			else
				return false;
		}

		static boolean isSubjectOrdinalNumberInvalid(byte subjOrdinalNumber) {
			if (subjOrdinalNumber < 1 || subjOrdinalNumber > 7)
				return true;
			else
				return false;
		}

		static boolean isIdInvalid(int id) {
			return id < 1 ? true : false;
		}

		static boolean isNull(Object value) {
			if (value == null) {
				return true;
			}
			return false;
		}

		static boolean isNameInvalid(String name) {
			if (isNull(name) || name.isEmpty()) {
				return true;
			}
			return false;
		}

		static boolean isBuildingInvalid(byte building) {
			if (building <= LOW_BUILDIN_NUMBER_BOUND || building > TOP_BUILDIN_NUMBER_BOUND)
				return true;
			return false;
		}

		static boolean isNumberInvalid(short number) {
			if (number <= LOW_CLASSROOM_NUMBER_BOUND || number > TOP_CLASSROOM_NUMBER_BOUND)
				return true;
			return false;
		}

		static boolean isEducationLevelInvalid(byte educationLevel) {
			if (educationLevel < LOW_EDUCATION_LEVEL_BOUND || educationLevel > TOP_EDUCATION_LEVEL_BOUND) {
				return true;
			}
			return false;
		}
	}
}