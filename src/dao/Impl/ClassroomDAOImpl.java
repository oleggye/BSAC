package dao.Impl;

import dao.IClassroomDAO;
import hibernateFiles.entity.Classroom;

public class ClassroomDAOImpl extends AbstractHibernateDAO<Classroom> implements IClassroomDAO {

	public ClassroomDAOImpl() {
		super(Classroom.class);
	}
}