package by.bsac.timetable.service.factory;

import by.bsac.timetable.service.IChairService;
import by.bsac.timetable.service.IClassroomService;
import by.bsac.timetable.service.IFacultyService;
import by.bsac.timetable.service.IFlowService;
import by.bsac.timetable.service.IGroupService;
import by.bsac.timetable.service.ILecturerService;
import by.bsac.timetable.service.IRecordService;
import by.bsac.timetable.service.ISubjectService;
import by.bsac.timetable.service.IValidationService;

public interface IServiceFactory {

	public IChairService getChairService();

	public IFacultyService getFacultyService();

	public IGroupService getGroupService();

	public IRecordService getRecordService();

	public IClassroomService getClassroomService();

	public ILecturerService getLecturerService();

	public ISubjectService getSubjectService();

	public IFlowService getFlowService();

	public IValidationService getValidationService();
}