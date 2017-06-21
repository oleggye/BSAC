package by.bsac.timetable.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import by.bsac.timetable.dao.ILecturerDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.hibernateFiles.HibernateUtil;
import by.bsac.timetable.hibernateFiles.entity.Chair;
import by.bsac.timetable.hibernateFiles.entity.Lecturer;

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

			HibernateUtil.commitTransaction();

		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			throw new DAOException(e.getMessage(), e);
		}
		return lecturerList;
	}

	public List<Lecturer> getAllWithSimilarName(String nameLecturer) throws DAOException {

		List<Lecturer> lecturerList = new ArrayList<>();
		try {
			Session session = HibernateUtil.getSession();
			HibernateUtil.beginTransaction();
			Criteria criteria = session.createCriteria(Lecturer.class, "lecturer");
			criteria.add(Restrictions.ilike("lecturer.nameLecturer", nameLecturer, MatchMode.START));
			lecturerList = criteria.list();
			HibernateUtil.commitTransaction();

		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
		return lecturerList;
	}
}