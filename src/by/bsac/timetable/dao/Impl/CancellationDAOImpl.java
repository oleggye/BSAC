package by.bsac.timetable.dao.Impl;

import by.bsac.timetable.dao.ICancellationDAO;
import by.bsac.timetable.hibernateFiles.entity.Cancellation;

public class CancellationDAOImpl extends AbstractHibernateDAO<Cancellation> implements ICancellationDAO {
	public CancellationDAOImpl() {
		super(Cancellation.class);
	}
}