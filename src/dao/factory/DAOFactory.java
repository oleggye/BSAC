package dao.factory;

import dao.ICancellationDAO;
import dao.IChairDAO;
import dao.IClassroomDAO;
import dao.IFacultyDAO;
import dao.IGroupDAO;
import dao.ILecturerDAO;
import dao.IRecordDAO;
import dao.ISubjectDAO;
import dao.Impl.CancellationDAOImpl;
import dao.Impl.ChairDAOImpl;
import dao.Impl.ClassroomDAOImpl;
import dao.Impl.FacultyDAOImpl;
import dao.Impl.GroupDAOImpl;
import dao.Impl.LecturerDAOImpl;
import dao.Impl.RecordDAOImpl;
import dao.Impl.SubjectDAOImpl;

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
