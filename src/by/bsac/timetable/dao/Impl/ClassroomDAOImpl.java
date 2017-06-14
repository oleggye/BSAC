package by.bsac.timetable.dao.Impl;

import by.bsac.timetable.dao.IClassroomDAO;
import by.bsac.timetable.hibernateFiles.entity.Classroom;

public class ClassroomDAOImpl extends AbstractHibernateDAO<Classroom> implements IClassroomDAO {

	public ClassroomDAOImpl() {
		super(Classroom.class);
	}
}