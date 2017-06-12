/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.Impl;

import hibernateFiles.HibernateUtil;
import hibernateFiles.entity.Group;
import hibernateFiles.entity.Record;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import dao.IRecordDAO;
import dao.exception.DAOException;

public class RecordDAOImpl extends AbstractHibernateDAO<Record> implements IRecordDAO {

	public RecordDAOImpl() {
		super(Record.class);
	}

	// TODO: изменить метод!
	@Override
	public List<Record> getRecordListByGroupAndDate(Group group, Date dateFrom, Date dateTo) throws DAOException {

		List<Record> recordList = new ArrayList<>();
		try {
			Session session = HibernateUtil.getSession();
			HibernateUtil.beginTransaction();

			Criteria criteria = session.createCriteria(Record.class, "record");
			criteria.add(Restrictions.eq("record.group", group));
			// riteria.add(Restrictions.("eduLevel", eduLevel));
			recordList = criteria.list();

			// short group_id = group.getIdGroup();
			// Query query = session
			// .createQuery("from Record as rec inner join rec.group as gr where
			// gr.idGroup = :group_id")
			// .setShort("group_id", group_id);
			// mainRecords = query.list();
			HibernateUtil.commitTransaction();

		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			throw new DAOException(e.getMessage(), e);
		}
		return recordList;
	}
}