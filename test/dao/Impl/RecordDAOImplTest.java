package dao.Impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.IGroupDAO;
import dao.IRecordDAO;
import dao.exception.DAOException;
import dao.factory.DAOFactory;
import hibernateFiles.entity.Group;
import hibernateFiles.entity.Record;

public class RecordDAOImplTest {

	private IRecordDAO recordDAO;
	private IGroupDAO groupDAO;

	@Before
	public void setUp() throws Exception {
		recordDAO = DAOFactory.getInstance().getRecordDAO();
		groupDAO = DAOFactory.getInstance().getGroupDAO();
	}

	@After
	public void tearDown() throws Exception {
		recordDAO = null;
		groupDAO = null;
	}

	@Test
	public void test() throws DAOException {
		Group group = groupDAO.getById((short)1);
		List<Record> recordList = recordDAO.getRecordListByGroupAndDate(group, null, null);
		System.out.println(recordList);
	}

}
