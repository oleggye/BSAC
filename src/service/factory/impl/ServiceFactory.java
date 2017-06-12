package service.factory.impl;

import service.IChairService;
import service.IClassroomService;
import service.IFacultyService;
import service.IGroupService;
import service.ILecturerService;
import service.ISubjectService;
import service.IValidationService;
import service.factory.IServiceFactory;
import service.IRecordService;
import service.impl.ChairServiceImpl;
import service.impl.ClassroomServiceImpl;
import service.impl.FacultyServiceImpl;
import service.impl.GroupServiceImpl;
import service.impl.LecturerServiceImpl;
import service.impl.SubjectServiceImpl;
import service.impl.ValidationServiceImpl;
import service.impl.RecordServiceImpl;

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