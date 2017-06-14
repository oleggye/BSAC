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
import by.bsac.timetable.service.impl.choke.ChairServiceChoke;
import by.bsac.timetable.service.impl.choke.ClassroomServiceChoke;
import by.bsac.timetable.service.impl.choke.FacultyServiceChoke;
import by.bsac.timetable.service.impl.choke.GroupServiceChoke;
import by.bsac.timetable.service.impl.choke.LecturerServiceChoke;
import by.bsac.timetable.service.impl.choke.RecordServiceChoke;
import by.bsac.timetable.service.impl.choke.SubjectServiceChoke;

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