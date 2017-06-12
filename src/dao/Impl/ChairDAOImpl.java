package dao.Impl;

import hibernateFiles.entity.Chair;
import dao.IChairDAO;

public class ChairDAOImpl extends AbstractHibernateDAO<Chair> implements IChairDAO {

	public ChairDAOImpl() {
		super(Chair.class);
	}
}