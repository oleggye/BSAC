package dao.Impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import by.bsac.timetable.dao.IChairDAO;
import by.bsac.timetable.dao.Impl.ChairDAOImpl;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.dao.factory.DAOFactory;
import by.bsac.timetable.hibernateFiles.entity.Chair;

public class ChairDAOImplTest extends ChairDAOImpl {
	
	private IChairDAO dao;

	@Before
	public void setUp() throws Exception {
		dao = DAOFactory.getInstance().getChairDAO();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws DAOException {
		List<Chair> chairList = dao.getAll();
		System.out.println(chairList);
	}

}
