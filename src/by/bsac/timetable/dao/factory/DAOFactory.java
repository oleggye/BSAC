package by.bsac.timetable.dao.factory;

import by.bsac.timetable.dao.ICancellationDAO;
import by.bsac.timetable.dao.IChairDAO;
import by.bsac.timetable.dao.IClassroomDAO;
import by.bsac.timetable.dao.IFacultyDAO;
import by.bsac.timetable.dao.IGroupDAO;
import by.bsac.timetable.dao.ILecturerDAO;
import by.bsac.timetable.dao.IRecordDAO;
import by.bsac.timetable.dao.ISubjectDAO;
import by.bsac.timetable.dao.Impl.CancellationDAOImpl;
import by.bsac.timetable.dao.Impl.ChairDAOImpl;
import by.bsac.timetable.dao.Impl.ClassroomDAOImpl;
import by.bsac.timetable.dao.Impl.FacultyDAOImpl;
import by.bsac.timetable.dao.Impl.GroupDAOImpl;
import by.bsac.timetable.dao.Impl.LecturerDAOImpl;
import by.bsac.timetable.dao.Impl.RecordDAOImpl;
import by.bsac.timetable.dao.Impl.SubjectDAOImpl;

public class DAOFactory {

	private final static DAOFactory INSTANCE = new DAOFactory();

	private final IChairDAO chairDAO = new ChairDAOImpl();
	private final IFacultyDAO facultyDAO = new FacultyDAOImpl();
	private final IGroupDAO groupDAO = new GroupDAOImpl();
	private final ILecturerDAO lecturerDAO = new LecturerDAOImpl();
	private final IRecordDAO RecordDAO = new RecordDAOImpl();
	private final ISubjectDAO SubjectDAO = new SubjectDAOImpl();
	private final IClassroomDAO classroomDAO = new ClassroomDAOImpl();
	private final ICancellationDAO cancellationDAO = new CancellationDAOImpl();

	public static DAOFactory getInstance() {
		return INSTANCE;
	}

	public IChairDAO getChairDAO() {
		return chairDAO;
	}

	public IFacultyDAO getFacultyDAO() {
		return facultyDAO;
	}

	public IGroupDAO getGroupDAO() {
		return groupDAO;
	}

	public ILecturerDAO getLecturerDAO() {
		return lecturerDAO;
	}

	public IRecordDAO getRecordDAO() {
		return RecordDAO;
	}

	public ISubjectDAO getSubjectDAO() {
		return SubjectDAO;
	}

	public IClassroomDAO getClassroomDAO() {
		return classroomDAO;
	}

	public ICancellationDAO getCancellationDAO() {
		return cancellationDAO;
	}

}
