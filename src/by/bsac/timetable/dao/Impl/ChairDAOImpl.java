package by.bsac.timetable.dao.Impl;

import by.bsac.timetable.dao.IChairDAO;
import by.bsac.timetable.hibernateFiles.entity.Chair;

public class ChairDAOImpl extends AbstractHibernateDAO<Chair> implements IChairDAO {

	public ChairDAOImpl() {
		super(Chair.class);
	}
}