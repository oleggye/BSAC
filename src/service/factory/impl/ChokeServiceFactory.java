package service.factory.impl;

import service.IChairService;
import service.IClassroomService;
import service.IFacultyService;
import service.IGroupService;
import service.ILecturerService;
import service.IRecordService;
import service.ISubjectService;
import service.IValidationService;
import service.factory.IServiceFactory;
import service.impl.choke.ChairServiceChoke;
import service.impl.choke.ClassroomServiceChoke;
import service.impl.choke.FacultyServiceChoke;
import service.impl.choke.GroupServiceChoke;
import service.impl.choke.LecturerServiceChoke;
import service.impl.choke.RecordServiceChoke;
import service.impl.choke.SubjectServiceChoke;

public class ChokeServiceFactory implements IServiceFactory {

	private static final IServiceFactory INSTANCE = new ChokeServiceFactory();

	private final IChairService chairsService = new ChairServiceChoke();
	private final IFacultyService facuiltiesService = new FacultyServiceChoke();
	private final IGroupService groupsService = new GroupServiceChoke();
	private final IRecordService classesService = new RecordServiceChoke();
	private final IClassroomService classroomService = new ClassroomServiceChoke();
	private final ILecturerService lecturersService = new LecturerServiceChoke();
	private final ISubjectService subjectsService = new SubjectServiceChoke();

	private ChokeServiceFactory() {
	}

	public static IServiceFactory getInstance() {
		return INSTANCE;
	}

	public IChairService getChairService() {
		return chairsService;
	}

	public IFacultyService getFacultyService() {
		return facuiltiesService;
	}

	public IGroupService getGroupService() {
		return groupsService;
	}

	public IRecordService getRecordService() {
		return classesService;
	}

	public IClassroomService getClassroomService() {
		return classroomService;
	}

	public ILecturerService getLecturerService() {
		return lecturersService;
	}

	public ISubjectService getSubjectService() {
		return subjectsService;
	}

	@Override
	public IValidationService getValidationService() {
		// TODO Auto-generated method stub
		return null;
	}
}