package by.bsac.timetable.dao.Impl;

import by.bsac.timetable.dao.IFlowDAO;
import by.bsac.timetable.hibernateFiles.entity.Flow;

public class FlowDAOImpl extends AbstractHibernateDAO<Flow> implements IFlowDAO {

	public FlowDAOImpl() {
		super(Flow.class);
	}
}