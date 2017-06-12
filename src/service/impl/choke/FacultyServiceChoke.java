package service.impl.choke;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hibernateFiles.entity.Faculty;
import hibernateFiles.entity.builder.FacultyBuilder;
import service.IFacultyService;
import service.exception.ServiceException;

public class FacultyServiceChoke implements IFacultyService {

	private static List<Faculty> facultyList = new ArrayList<>();

	static {
		Faculty faculty1 = new FacultyBuilder().buildId((byte) 1).buildId("Электросвязи").build();
		Faculty faculty2 = new FacultyBuilder().buildId((byte) 2).buildId("Почта").build();
		Faculty faculty3 = new FacultyBuilder().buildId((byte) 3).buildId("Что-то еще").build();
		Faculty faculty4 = new FacultyBuilder().buildId((byte) 4).buildId("Еще что-то").build();
		facultyList = Arrays.asList(faculty1, faculty2, faculty3, faculty4);
	}

	@Override
	public void addFaculties(Faculty faculty) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateFaculty(Faculty faculty) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public Faculty getFacultyById(int faculty_id) throws ServiceException {
		// TODO Auto-generated method stub
		return facultyList.get(faculty_id - 1);
	}

	@Override
	public List<Faculty> getAllFaculties() throws ServiceException {
		return facultyList;
	}

	@Override
	public void deleteFaculty(Faculty faculty) throws ServiceException {
		// TODO Auto-generated method stub

	}

}
