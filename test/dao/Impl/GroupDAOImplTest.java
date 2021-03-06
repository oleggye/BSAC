package dao.Impl;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import by.bsac.timetable.dao.IGroupDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.dao.factory.DAOFactory;
import by.bsac.timetable.hibernateFiles.entity.Faculty;
import by.bsac.timetable.hibernateFiles.entity.Group;
import by.bsac.timetable.hibernateFiles.entity.builder.FacultyBuilder;
import by.bsac.timetable.hibernateFiles.entity.builder.GroupBuilder;

public class GroupDAOImplTest {

	private IGroupDAO dao;
	private Faculty faculty;
	private byte eduLevel;

	@Before
	public void setUp() throws Exception {
		dao = DAOFactory.getInstance().getGroupDAO();
		faculty = new FacultyBuilder().buildId((byte) 1).build();
		eduLevel = 2;
	}

	@After
	public void tearDown() throws Exception {
		dao = null;
	}

//	@Test
//	public void testGetAll() throws DAOException {
//		List<Group> groupList = dao.getAll();
//		System.out.println(groupList);
//	}
//
//	@Test
//	public void testGetById() throws DAOException {
//		Group group = dao.getById(new Short((short) 1));
//		System.out.println(group);
//	}

	@Test
	public void testGetGroupByFacultyAnd()throws DAOException{
		for(int i =0;i<10;i++){
		List<Group> groupList = dao.getGroupListByFacultyAndEduLevel(faculty,eduLevel);
		}
		// System.out.println(groupList);
	}
}