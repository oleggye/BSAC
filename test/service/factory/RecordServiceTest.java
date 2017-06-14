package service.factory;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import by.bsac.timetable.hibernateFiles.entity.Faculty;
import by.bsac.timetable.hibernateFiles.entity.Group;
import by.bsac.timetable.hibernateFiles.entity.Record;
import by.bsac.timetable.hibernateFiles.entity.builder.FacultyBuilder;
import by.bsac.timetable.hibernateFiles.entity.builder.GroupBuilder;
import by.bsac.timetable.service.IRecordService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.factory.IServiceFactory;
import by.bsac.timetable.service.factory.ServiceFactoryProvider;

public class RecordServiceTest {

	private IRecordService service;
	private Group group;
	private Date dateFrom;
	private Date dateTo;
	

	@Before
	public void setUp() throws Exception {
		ServiceFactoryProvider provider = ServiceFactoryProvider.getInstance();
		IServiceFactory factory = provider.getServiceFactory();
		service = factory.getRecordService();
		
		buildGroup();
		dateFrom = Calendar.getInstance().getTime();
		dateTo = new Date("2017/06/01");
	}

	@After
	public void tearDown() throws Exception {
		service = null;
		group = null;
		dateFrom = null;
		dateTo = null;
	}

	@Test
	public void test() throws ServiceException {
		List<Record> recordList = service.getAllRecordListForGroup(group, dateFrom, dateTo);
		System.out.println(recordList);
	}

	private void buildGroup() {
		Faculty faculty = new FacultyBuilder()
								.buildId((byte)1)
								.build();
		
		group = new GroupBuilder().buildFaculty(faculty)
								  .buildEduLevel((byte)1)
								  .buildId((short)1)
								  .buildName("СП442")
								  .build();
	}
}