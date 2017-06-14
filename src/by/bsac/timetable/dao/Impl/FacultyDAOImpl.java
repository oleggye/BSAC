package by.bsac.timetable.dao.Impl;

import by.bsac.timetable.dao.IFacultyDAO;
import by.bsac.timetable.hibernateFiles.entity.Faculty;

public class FacultyDAOImpl extends AbstractHibernateDAO<Faculty> implements IFacultyDAO {

	public FacultyDAOImpl() {
		super(Faculty.class);
	}
}