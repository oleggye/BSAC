package service;

import hibernateFiles.entity.Faculty;
import java.util.List;
import service.exception.ServiceException;

public interface IFacultyService {

	public void addFaculties(Faculty faculty) throws ServiceException;

	public void updateFaculty(Faculty faculty) throws ServiceException;

	public Faculty getFacultyById(int faculty_id) throws ServiceException;

	public List<Faculty> getAllFaculties() throws ServiceException;

	public void deleteFaculty(Faculty faculty) throws ServiceException;
}