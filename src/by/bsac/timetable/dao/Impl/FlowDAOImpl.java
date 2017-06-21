package by.bsac.timetable.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import by.bsac.timetable.dao.IFlowDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.hibernateFiles.HibernateUtil;
import by.bsac.timetable.hibernateFiles.entity.Flow;

public class FlowDAOImpl extends AbstractHibernateDAO<Flow> implements IFlowDAO {

	public FlowDAOImpl() {
		super(Flow.class);
	}

	@Override
	public List<Flow> getAllWithSimilarName(String nameFlow) throws DAOException {
		List<Flow> flowList = new ArrayList<>();
		try {
			Session session = HibernateUtil.getSession();
			HibernateUtil.beginTransaction();
			Criteria criteria = session.createCriteria(Flow.class, "flow");
			criteria.add(Restrictions.ilike("flow.name", nameFlow, MatchMode.START));
			flowList = criteria.list();
			HibernateUtil.commitTransaction();

		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
		return flowList;
	}
}