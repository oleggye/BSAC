package by.bsac.timetable.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import by.bsac.timetable.dao.IRecordDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.hibernateFiles.HibernateUtil;
import by.bsac.timetable.hibernateFiles.entity.Group;
import by.bsac.timetable.hibernateFiles.entity.Record;

public class RecordDAOImpl extends AbstractHibernateDAO<Record> implements IRecordDAO {

	public RecordDAOImpl() {
		super(Record.class);
	}

	/*
	 * // TODO: изменить метод!
	 * 
	 * @Override public List<Record> getRecordListByGroupAndDate(Group group,
	 * Date dateFrom, Date dateTo) throws DAOException {
	 * 
	 * List<Record> recordList = new ArrayList<>(); try { Session session =
	 * HibernateUtil.getSession(); HibernateUtil.beginTransaction();
	 * 
	 * Criteria criteria = session.createCriteria(Record.class, "record");
	 * criteria.add(Restrictions.eq("record.group", group)); //
	 * riteria.add(Restrictions.("eduLevel", eduLevel)); recordList =
	 * criteria.list();
	 * 
	 * // short group_id = group.getIdGroup(); // Query query = session //
	 * .createQuery("from Record as rec inner join rec.group as gr where //
	 * gr.idGroup = :group_id") // .setShort("group_id", group_id); //
	 * mainRecords = query.list(); HibernateUtil.commitTransaction();
	 * 
	 * } catch (Exception e) { HibernateUtil.rollbackTransaction(); throw new
	 * DAOException(e.getMessage(), e); } return recordList; }
	 */

	@Override
	public List<Record> getRecordListByGroupAndDate(Group group, Date dateFrom, Date dateTo) throws DAOException {

		List<Record> recordList = new ArrayList<>();
		try {
			Session session = HibernateUtil.getSession();
			HibernateUtil.beginTransaction();
			SQLQuery sqlQuery = session
					.createSQLQuery("SELECT *"
							+ " FROM timetable.cancellation as cancel right join timetable.record as rec using(id_record)"
							+ " where rec.id_group = ? "
							+ " and	not((rec.date_to < ?) or (rec.date_from > ?))"
							+ " and (cancel.id_record is null or ((cancel.date_to <rec.date_from) or (cancel.date_from > rec.date_to)))")
					.addEntity(Record.class);
			sqlQuery.setShort(0, group.getIdGroup());
			sqlQuery.setDate(1, dateFrom);
			sqlQuery.setDate(2, dateTo);

			recordList = sqlQuery.list();
			HibernateUtil.commitTransaction();

		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			throw new DAOException(e.getMessage(), e);
		}
		return recordList;
	}

	@Override
	public Record getRecordForGroupLikeThis(Group group, Record record) throws DAOException {

		Record likeThisRecord = null;
		try {
			Session session = HibernateUtil.getSession();
			HibernateUtil.beginTransaction();

			Criteria criteria = session.createCriteria(Record.class, "record");
			criteria.add(Restrictions.eq("record.group", group));
			criteria.add(Restrictions.eq("record.weekNumber", record.getWeekNumber()));
			criteria.add(Restrictions.eq("record.weekDay", record.getWeekDay()));
			criteria.add(Restrictions.eq("record.subjOrdinalNumber", record.getSubjOrdinalNumber()));
			criteria.add(Restrictions.eq("record.dateFrom", record.getDateFrom()));
			criteria.add(Restrictions.eq("record.dateTo", record.getDateTo()));
			likeThisRecord = (Record) criteria.uniqueResult();

			HibernateUtil.commitTransaction();

		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			throw new DAOException(e.getMessage(), e);
		} finally {
			HibernateUtil.closeSession();
		}
		return likeThisRecord;
	}
}