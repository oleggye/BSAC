package dao.Impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.exception.DAOException;
import hibernateFiles.entity.Faculty;

public class FacultyDAOImplTest {

	private FacultyDAOImpl dao;

	@Before
	public void setUp() throws Exception {
		dao = new FacultyDAOImpl();
	}

	@After
	public void tearDown() throws Exception {
		dao = null;
	}

	@Test
	public void test() throws DAOException {
		Faculty faculty = dao.getById((byte) 1);
		System.out.println(faculty.getGroups());
	}
}