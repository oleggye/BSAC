package dao.Impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import by.bsac.timetable.dao.IClassroomDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.dao.factory.DAOFactory;
import by.bsac.timetable.hibernateFiles.entity.Classroom;
import by.bsac.timetable.hibernateFiles.entity.builder.ClassroomBuilder;

public class ClassroomDAOImplTest {

	private IClassroomDAO dao;
	private Classroom classroom;

	@Before
	public void setUp() throws Exception {
		dao = DAOFactory.getInstance().getClassroomDAO();

		classroom = new ClassroomBuilder().buildId((short) 1).buildNumber((short) 123).buildBuilding((byte) 1).build();
	}

	@After
	public void tearDown() throws Exception {
		dao = null;
		classroom = null;
	}

	@Test
	public void test() throws DAOException {
		dao.add(classroom);
	}
}