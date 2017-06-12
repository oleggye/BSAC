package dao.Impl;

import hibernateFiles.HibernateUtil;
import hibernateFiles.entity.Chair;
import hibernateFiles.entity.Lecturer;
import hibernateFiles.entity.Subject;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import dao.ILecturerDAO;
import dao.exception.DAOException;

public class LecturerDAOImpl extends AbstractHibernateDAO<Lecturer> implements ILecturerDAO {

	public LecturerDAOImpl() {
		super(Lecturer.class);
	}

	@Override
	public List<Lecturer> getLecturerListByChair(Chair chair) throws DAOException {

		List<Lecturer> lecturerList = new ArrayList<>();
		try {
			Session session = HibernateUtil.getSession();
			HibernateUtil.beginTransaction();

			Criteria criteria = session.createCriteria(Lecturer.class, "lecturer");
			criteria.add(Restrictions.eq("lecturer.chair", chair));
			lecturerList = criteria.list();

			// byte chair_id = chair.getIdChair();
			// Query query = session
			// .createQuery("from Lecturer as lc inner join lc.chair as ch" + "
			// where ch.idChair =:chair_id")
			// .setByte("chair_id", chair_id);
			// lecturersRecords = query.list();

			HibernateUtil.commitTransaction();

		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			throw new DAOException(e.getMessage(), e);
		}
		return lecturerList;
	}
}