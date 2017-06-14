package by.bsac.timetable.service;

import by.bsac.timetable.hibernateFiles.entity.Chair;
import by.bsac.timetable.hibernateFiles.entity.Classroom;
import by.bsac.timetable.hibernateFiles.entity.Faculty;
import by.bsac.timetable.hibernateFiles.entity.Record;
import by.bsac.timetable.service.exception.ServiceValidationException;

public interface IValidationService {

	public void validateRecord(Record record) throws ServiceValidationException;

	public void validateFaculty(Faculty faculty) throws ServiceValidationException;

	public void validateChair(Chair chair) throws ServiceValidationException;

	public void validateClassroom(Classroom classroom) throws ServiceValidationException;
}