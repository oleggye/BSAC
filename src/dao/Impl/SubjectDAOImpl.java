package dao.Impl;

import hibernateFiles.HibernateUtil;
import hibernateFiles.entity.Chair;
import hibernateFiles.entity.Subject;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import dao.ISubjectDAO;
import dao.exception.DAOException;

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

			// byte chair_id = chair.getIdChair();
			// Query query = session
			// .createQuery("from Subject as sb inner join sb.chair as ch" + "
			// where ch.idChair =:chair_id")
			// .setByte("chair_id", chair_id);
			// subjectList = query.list();

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

			// Byte chair_id = chair.getIdChair();
			// Query query = session
			// .createQuery("from Subject as sb inner join sb.chair as ch "
			// + "where ch.idChair =:chair_id and sb.eduLevel =:edu_level")
			// .setByte("chair_id", chair_id).setInteger("edu_level", eduLevel);
			// subjectsRecords = query.list();

			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			throw new DAOException(e.getMessage(), e);
		}
		return subjectList;
	}
}