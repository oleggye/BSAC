package service.impl;

import java.util.Date;

import hibernateFiles.entity.Classroom;
import hibernateFiles.entity.Group;
import hibernateFiles.entity.Lecturer;
import hibernateFiles.entity.Record;
import hibernateFiles.entity.Subject;
import hibernateFiles.entity.SubjectFor;
import hibernateFiles.entity.SubjectType;
import service.IValidationService;
import service.exception.ServiceValidationException;

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
			throw new ServiceValidationException("Не верный номер недели: !" + weekNumber);
		}
		byte weekDay = record.getWeekDay();
		if (Checker.isWeekDayInvalid(weekDay)) {
			throw new ServiceValidationException("Не верный номер дня недели: !" + weekDay);
		}
		byte subjectOrdinalNumber = record.getSubjOrdinalNumber();
		if (Checker.isSubjectOrdinalNumberInvalid(subjectOrdinalNumber)) {
			throw new ServiceValidationException("Не верный порядковый номер занятия: !" + subjectOrdinalNumber);
		}

	}

	private static class Checker {

		static boolean isDatesInvalid(Date dateFrom, Date dateTo) {

			if (isNull(dateFrom) || isNull(dateTo) || dateFrom.compareTo(dateTo) > 0
					|| dateTo.compareTo(dateFrom) < 0) {
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
	}
}