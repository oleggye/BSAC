package service;

import java.time.LocalDate;
import java.util.List;

import hibernateFiles.entity.Classroom;
import hibernateFiles.entity.Group;
import service.exception.ServiceException;

public interface IClassroomService {

	public void addClassroom(Classroom classRoom) throws ServiceException;

	public void updateClassroom(Classroom classRoom) throws ServiceException;

	public Classroom getClassroom(int idClassroom) throws ServiceException;

	public void deleteClassroom(Classroom classRoom) throws ServiceException;

	public List<Classroom> getClassroomListForGroup(Group group, LocalDate dateFrom, LocalDate dateTo)
			throws ServiceException;

	public List<Classroom> getClassroomList() throws ServiceException;
}