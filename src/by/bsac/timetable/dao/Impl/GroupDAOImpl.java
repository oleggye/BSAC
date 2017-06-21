package by.bsac.timetable.dao.Impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import by.bsac.timetable.dao.IGroupDAO;
import by.bsac.timetable.dao.IRecordDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.dao.factory.DAOFactory;
import by.bsac.timetable.hibernateFiles.HibernateUtil;
import by.bsac.timetable.hibernateFiles.entity.Faculty;
import by.bsac.timetable.hibernateFiles.entity.Flow;
import by.bsac.timetable.hibernateFiles.entity.Group;
import by.bsac.timetable.hibernateFiles.entity.Record;
import by.bsac.timetable.hibernateFiles.entity.SubjectFor;
import by.bsac.timetable.util.LessonFor;

public class GroupDAOImpl extends AbstractHibernateDAO<Group> implements IGroupDAO {

	public GroupDAOImpl() {
		super(Group.class);
	}

	@Override
	public List<Group> getGroupListByFaculty(Faculty faculty) throws DAOException {

		List<Group> groupsRecords = new ArrayList<>();
		try {
			Session session = HibernateUtil.getSession();
			short faculty_id = faculty.getIdFaculty();
			HibernateUtil.beginTransaction();
			Query query = session.createQuery("select gr from Group as gr" + " inner join gr.Faculty as faculty "
					+ "where faculty.idFaculty = :faculty_id").setShort("faculty_id", faculty_id);
			groupsRecords = query.list();
			HibernateUtil.commitTransaction();

		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			throw new DAOException(e.getMessage(), e);
		}
		return groupsRecords;
	}

	@Override
	public List<Group> getGroupListByFacultyAndEduLevel(Faculty faculty, byte eduLevel) throws DAOException {

		List<Group> groupsRecords = new ArrayList<>();
		try {
			Session session = HibernateUtil.getSession();
			HibernateUtil.beginTransaction();
			Criteria criteria = session.createCriteria(Group.class, "group");
			criteria.add(Restrictions.eq("group.faculty", faculty));
			criteria.add(Restrictions.eq("eduLevel", eduLevel));
			groupsRecords = criteria.list();

			HibernateUtil.commitTransaction();

		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			throw new DAOException(e.getMessage(), e);
		}
		return groupsRecords;
	}

	@Override
	public List<Group> getGroupListByFlow(Flow flow) throws DAOException {
		List<Group> groupList = new ArrayList<>();
		try {
			Session session = HibernateUtil.getSession();
			HibernateUtil.beginTransaction();
			Criteria criteria = session.createCriteria(Group.class, "group");
			criteria.add(Restrictions.eq("group.flow", flow));
			groupList = criteria.list();
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			throw new DAOException(e.getMessage(), e);
		}
		return groupList;
	}

	@Override
	public void changeGroupFlow(Group group, Flow newFlow) throws DAOException {

		SubjectFor subjectFor = (LessonFor.FULL_FLOW).lessonForToSubjectFor();
		try {
			IRecordDAO dao = DAOFactory.getInstance().getRecordDAO();
			List<Record> recordList = dao.getRecordListByGroupAndSubjectFor(group, subjectFor);

			Session session = HibernateUtil.getSession();
			HibernateUtil.beginTransaction();
			
			SQLQuery deleteRecordSqlQuery = session
					.createSQLQuery("delete from timetable.record where record.id_record = ?;");

			Iterator<Record> iter = recordList.listIterator();
			while (iter.hasNext()) {
				Integer idRecord = iter.next().getIdRecord();
				deleteRecordSqlQuery.setInteger(0, idRecord);
				deleteRecordSqlQuery.executeUpdate();
			}
			group.setFlow(newFlow);
			session.update(group);

			HibernateUtil.commitTransaction();

		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<Group> getAllWithSimilarName(String nameLecturer) throws DAOException {
		List<Group> groupList = new ArrayList<>();
		try {
			Session session = HibernateUtil.getSession();
			HibernateUtil.beginTransaction();
			Criteria criteria = session.createCriteria(Group.class, "group");
			criteria.add(Restrictions.ilike("group.nameGroup", nameLecturer, MatchMode.START));
			groupList = criteria.list();
			HibernateUtil.commitTransaction();

		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
		return groupList;
	}
}