package dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import dao.IGenericDAO;
import dao.exception.DAOException;
import hibernateFiles.HibernateUtil;

public abstract class AbstractHibernateDAO<E> implements IGenericDAO<E> {

	private final Class<E> clazz;

	public AbstractHibernateDAO(Class<E> typeParameterClass) {
		this.clazz = typeParameterClass;
	}

	@Override
	public void add(E object) throws DAOException {
		try {
			Session session = HibernateUtil.getSession();
			HibernateUtil.beginTransaction();
			session.save(object);
			HibernateUtil.commitTransaction();

		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			throw new DAOException(e.getMessage(), e);
		} finally {
			HibernateUtil.closeSession();
		}
	}

	@Override
	public E getById(Number id) throws DAOException {
		E result = null;
		try {
			Session session = HibernateUtil.getSession();
			HibernateUtil.beginTransaction();
			result = (E) session.load(clazz, id);
			HibernateUtil.commitTransaction();

		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			throw new DAOException(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public List<E> getAll() throws DAOException {

		List<E> resultList = new ArrayList<>();
		try {
			Session session = HibernateUtil.getSession();
			HibernateUtil.beginTransaction();
			resultList = session.createCriteria(clazz).list();
			HibernateUtil.commitTransaction();

		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			throw new DAOException(e.getMessage(), e);
		}
		return resultList;
	}

	@Override
	public void update(E object) throws DAOException {
		try {
			Session session = HibernateUtil.getSession();
			HibernateUtil.beginTransaction();
			session.update(object);
			HibernateUtil.commitTransaction();

		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			throw new DAOException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(E object) throws DAOException {
		try {
			Session session = HibernateUtil.getSession();
			HibernateUtil.beginTransaction();
			session.delete(object);
			HibernateUtil.commitTransaction();
			session.flush();

		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			throw new DAOException(e.getMessage(), e);
		}
	}
}