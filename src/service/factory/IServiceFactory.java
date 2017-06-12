package service.factory;

import service.IChairService;
import service.IClassroomService;
import service.IFacultyService;
import service.IGroupService;
import service.ILecturerService;
import service.IRecordService;
import service.ISubjectService;
import service.IValidationService;

public interface IServiceFactory {

	public IChairService getChairService();

	public IFacultyService getFacultyService();

	public IGroupService getGroupService();

	public IRecordService getRecordService();

	public IClassroomService getClassroomService();

	public ILecturerService getLecturerService();

	public ISubjectService getSubjectService();

	public IValidationService getValidationService();
}