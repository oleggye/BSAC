package dao.Impl;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import by.bsac.timetable.dao.ILecturerDAO;
import by.bsac.timetable.dao.Impl.ChairDAOImpl;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.dao.factory.DAOFactory;
import by.bsac.timetable.hibernateFiles.entity.Lecturer;

public class LecturerDAOImplTest extends ChairDAOImpl {

	private ILecturerDAO dao;

	@Before
	public void setUp() throws Exception {
		dao = DAOFactory.getInstance().getLecturerDAO();
	}

	@After
	public void tearDown() throws Exception {
		dao = null;
	}

	@Test
	public void test() throws DAOException {
		List<Lecturer> lecturerList = dao.getAllWithSimilarName("Ряб");
		System.out.println(lecturerList);
	}

}
