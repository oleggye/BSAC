package dao.Impl;

import hibernateFiles.entity.Faculty;
import dao.IFacultyDAO;

public class FacultyDAOImpl extends AbstractHibernateDAO<Faculty> implements IFacultyDAO {

	public FacultyDAOImpl() {
		super(Faculty.class);
	}
}