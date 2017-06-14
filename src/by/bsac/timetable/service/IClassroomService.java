package by.bsac.timetable.service;

import java.time.LocalDate;
import java.util.List;

import by.bsac.timetable.hibernateFiles.entity.Classroom;
import by.bsac.timetable.hibernateFiles.entity.Group;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;

public interface IClassroomService {

	public void addClassroom(Classroom classRoom) throws ServiceException, ServiceValidationException;;

	public void updateClassroom(Classroom classRoom) throws ServiceException, ServiceValidationException;;

	public Classroom getClassroom(int idClassroom) throws ServiceException;

	public void deleteClassroom(Classroom classRoom) throws ServiceException;

	public List<Classroom> getClassroomListForGroup(Group group, LocalDate dateFrom, LocalDate dateTo)
			throws ServiceException;

	public List<Classroom> getClassroomList() throws ServiceException;
}