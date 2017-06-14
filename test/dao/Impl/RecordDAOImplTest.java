package dao.Impl;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import by.bsac.timetable.dao.IGroupDAO;
import by.bsac.timetable.dao.IRecordDAO;
import by.bsac.timetable.dao.Impl.RecordDAOImpl;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.dao.factory.DAOFactory;
import by.bsac.timetable.hibernateFiles.entity.Group;
import by.bsac.timetable.hibernateFiles.entity.Record;

public class RecordDAOImplTest {

	private IRecordDAO recordDAO;
	private IGroupDAO groupDAO;
	private Date dateTo;
	private Date dateFrom;

	@Before
	public void setUp() throws Exception {
		recordDAO = new RecordDAOImpl();
		groupDAO = DAOFactory.getInstance().getGroupDAO();
		dateTo = new Date("2017/06/15");
		dateFrom = new Date("2017/06/12");
	}

	@After
	public void tearDown() throws Exception {
		recordDAO = null;
		groupDAO = null;
		dateTo = null;
		dateFrom = null;
	}

	@Test
	public void test() throws DAOException {
		Group group = groupDAO.getById((short) 1);
		//List<Record> recordList = recordDAO.getRecordListByGroupAndDate(group, dateTo, dateFrom);
		List<Record> recordList = recordDAO.getRecordListByGroupAndDate(group, dateFrom, dateTo);
		System.out.println(recordList);
	}

}
