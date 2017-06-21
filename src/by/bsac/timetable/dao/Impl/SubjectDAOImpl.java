package by.bsac.timetable.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import by.bsac.timetable.dao.ISubjectDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.hibernateFiles.HibernateUtil;
import by.bsac.timetable.hibernateFiles.entity.Chair;
import by.bsac.timetable.hibernateFiles.entity.Subject;

public class SubjectDAOImpl extends AbstractHibernateDAO<Subject> implements ISubjectDAO {

	public SubjectDAOImpl() {
		super(Subject.class);
	}

	@Override
	public List<Subject> getSubjectListByChair(Chair chair) throws DAOException {

		List<Subject> subjectList = new ArrayList<>();
		try {
			Session session = HibernateUtil.getSession();
			HibernateUtil.beginTransaction();

			Criteria criteria = session.createCriteria(Subject.class, "subject");
			criteria.add(Restrictions.eq("subject.chair", chair));
			subjectList = criteria.list();

			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			throw new DAOException(e.getMessage(), e);
		}
		return subjectList;
	}

	@Override
	public List<Subject> getSubjectListByChairAndEduLevel(Chair chair, byte eduLevel) throws DAOException {

		List<Subject> subjectList = new ArrayList<>();
		try {
			Session session = HibernateUtil.getSession();
			HibernateUtil.beginTransaction();

			Criteria criteria = session.createCriteria(Subject.class, "subject");
			criteria.add(Restrictions.eq("subject.chair", chair));
			criteria.add(Restrictions.eq("eduLevel", eduLevel));
			subjectList = criteria.list();

			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			throw new DAOException(e.getMessage(), e);
		}
		return subjectList;
	}

	@Override
	public List<Subject> getAllWithSimilarName(String nameSubject) throws DAOException {
		List<Subject> subjectList = new ArrayList<>();
		try {
			Session session = HibernateUtil.getSession();
			HibernateUtil.beginTransaction();
			Criteria criteria = session.createCriteria(Subject.class, "subject");
			criteria.add(Restrictions.ilike("subject.nameSubject", nameSubject, MatchMode.START));
			subjectList = criteria.list();
			HibernateUtil.commitTransaction();

		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
		return subjectList;
	}
}