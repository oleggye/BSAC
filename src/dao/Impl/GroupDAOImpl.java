package dao.Impl;

import hibernateFiles.HibernateUtil;
import hibernateFiles.entity.Faculty;
import hibernateFiles.entity.Flow;
import hibernateFiles.entity.Group;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import dao.IGroupDAO;
import dao.exception.DAOException;

public class GroupDAOImpl extends AbstractHibernateDAO<Group> implements IGroupDAO {

	public GroupDAOImpl() {
		super(Group.class);
	}

	@Override
	public List<Group> getGroupListByFaculty(Faculty faculty) throws DAOException {

		List<Group> groupsRecords = new ArrayList<>();
		try {
			Session session = HibernateUtil.getSession();
			byte faculty_id = faculty.getIdFaculty();
			HibernateUtil.beginTransaction();
			Query query = session.createQuery("select gr from Group as gr" + " inner join gr.Faculty as faculty "
					+ "where faculty.idFaculty = :faculty_id").setByte("faculty_id", faculty_id);
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

			// byte faculty_id = faculty.getIdFaculty();
			// Query query = session
			// .createQuery("from Group as gr inner join gr.faculty as faculty "
			// + "where faculty.idFaculty = :faculty_id " + "and gr.eduLevel
			// =:edu_level")
			// .setByte("faculty_id", faculty_id).setInteger("edu_level",
			// eduLevel);
			// groupsRecords = query.list();
			HibernateUtil.commitTransaction();

		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			throw new DAOException(e.getMessage(), e);
		}
		return groupsRecords;
	}

	@Override
	public List<Group> getGroupByFlow(Flow flow) throws DAOException {
		List<Group> groupsRecords = new ArrayList<>();
		try {
			Session session = HibernateUtil.getSession();
			HibernateUtil.beginTransaction();
			Criteria criteria = session.createCriteria(Group.class, "group");
			criteria.add(Restrictions.eq("group.flow", flow));
			groupsRecords = criteria.list();
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			throw new DAOException(e.getMessage(), e);
		}
		return groupsRecords;
	}
}