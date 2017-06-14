package by.bsac.timetable.service.factory.impl;

import by.bsac.timetable.service.IChairService;
import by.bsac.timetable.service.IClassroomService;
import by.bsac.timetable.service.IFacultyService;
import by.bsac.timetable.service.IGroupService;
import by.bsac.timetable.service.ILecturerService;
import by.bsac.timetable.service.IRecordService;
import by.bsac.timetable.service.ISubjectService;
import by.bsac.timetable.service.IValidationService;
import by.bsac.timetable.service.factory.IServiceFactory;
import by.bsac.timetable.service.impl.ChairServiceImpl;
import by.bsac.timetable.service.impl.ClassroomServiceImpl;
import by.bsac.timetable.service.impl.FacultyServiceImpl;
import by.bsac.timetable.service.impl.GroupServiceImpl;
import by.bsac.timetable.service.impl.LecturerServiceImpl;
import by.bsac.timetable.service.impl.RecordServiceImpl;
import by.bsac.timetable.service.impl.SubjectServiceImpl;
import by.bsac.timetable.service.impl.ValidationServiceImpl;

/**
 *
 * @author hello
 */
public final class ServiceFactory implements IServiceFactory {

	private static final IServiceFactory INSTANCE = new ServiceFactory();

	private final IChairService chairService = new ChairServiceImpl();
	private final IFacultyService facuiltyService = new FacultyServiceImpl();
	private final IGroupService groupService = new GroupServiceImpl();
	private final IRecordService recordService = new RecordServiceImpl();
	private final IClassroomService classroomService = new ClassroomServiceImpl();
	private final ILecturerService lecturerService = new LecturerServiceImpl();
	private final ISubjectService subjectService = new SubjectServiceImpl();
	private final IValidationService validationService = new ValidationServiceImpl();
	

	private ServiceFactory() {
	}

	public static IServiceFactory getInstance() {
		return INSTANCE;
	}

	public IChairService getChairService() {
		return chairService;
	}

	public IFacultyService getFacultyService() {
		return facuiltyService;
	}

	public IGroupService getGroupService() {
		return groupService;
	}

	public IRecordService getRecordService() {
		return recordService;
	}

	public IClassroomService getClassroomService() {
		return classroomService;
	}

	public ILecturerService getLecturerService() {
		return lecturerService;
	}

	public ISubjectService getSubjectService() {
		return subjectService;
	}

	public IValidationService getValidationService() {
		return validationService;
	}
}