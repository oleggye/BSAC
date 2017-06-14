package by.bsac.timetable.service.impl.choke;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import by.bsac.timetable.hibernateFiles.entity.Classroom;
import by.bsac.timetable.hibernateFiles.entity.Group;
import by.bsac.timetable.hibernateFiles.entity.builder.ClassroomBuilder;
import by.bsac.timetable.service.IClassroomService;
import by.bsac.timetable.service.exception.ServiceException;

public class ClassroomServiceChoke implements IClassroomService {

	private static List<Classroom> classroomList = new ArrayList<>();

	static {
		Classroom classroom1 = new ClassroomBuilder().buildId((short) 1).buildNumber((short) 123)
				.buildBuilding((byte) 1).build();
		Classroom classroom2 = new ClassroomBuilder().buildId((short) 2).buildNumber((short) 312)
				.buildBuilding((byte) 2).build();
		Classroom classroom3 = new ClassroomBuilder().buildId((short) 3).buildNumber((short) 121)
				.buildBuilding((byte) 2).build();

		classroomList = Arrays.asList(classroom1, classroom2, classroom3);
	}

	@Override
	public void addClassroom(Classroom classRoom) throws ServiceException {
		// TODO Auto-generated method stub
		
		Classroom classroom1 = new ClassroomBuilder().buildId((short) 1).buildNumber((short) 123)
				.buildBuilding((byte) 1).build();

	}

	@Override
	public void updateClassroom(Classroom classRoom) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public Classroom getClassroom(int idClassroom) throws ServiceException {
		// TODO Auto-generated method stub
		return classroomList.get(idClassroom - 1);
	}

	@Override
	public void deleteClassroom(Classroom classRoom) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Classroom> getClassroomListForGroup(Group group, LocalDate dateFrom, LocalDate dateTo)
			throws ServiceException {
		// TODO Auto-generated method stub
		return classroomList;
	}

	@Override
	public List<Classroom> getClassroomList() throws ServiceException {
		return classroomList;
	}
}