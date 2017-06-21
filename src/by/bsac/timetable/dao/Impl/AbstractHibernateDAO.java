package by.bsac.timetable.dao.Impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import by.bsac.timetable.dao.IGenericDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.hibernateFiles.HibernateUtil;

public abstract class AbstractHibernateDAO<E> implements IGenericDAO<E> {
	private static final Logger LOGGER = LogManager.getLogger(AbstractHibernateDAO.class.getName());

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

		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			LOGGER.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.fatal(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			HibernateUtil.closeSession();
		}
	}

	@Override
	public void addAll(List<E> listObject) throws DAOException {
		try {
			Session session = HibernateUtil.getSession();
			HibernateUtil.beginTransaction();

			Iterator<E> iter = listObject.listIterator();
			while (iter.hasNext()) {
				session.save(iter.next());
			}

			HibernateUtil.commitTransaction();

		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			LOGGER.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.fatal(e.getMessage(), e);
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

		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			LOGGER.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.fatal(e.getMessage(), e);
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
			LOGGER.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.fatal(e.getMessage(), e);
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

		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			LOGGER.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.fatal(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			HibernateUtil.closeSession();
		}
	}

	@Override
	public void updateAll(List<E> listObject) throws DAOException {
		try {
			Session session = HibernateUtil.getSession();
			HibernateUtil.beginTransaction();

			Iterator<E> iter = listObject.listIterator();
			while (iter.hasNext()) {
				session.update(iter.next());
			}

			HibernateUtil.commitTransaction();

		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			LOGGER.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.fatal(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			HibernateUtil.closeSession();
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

		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			LOGGER.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.fatal(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	@Override
	public void deleteAll(List<E> listObject) throws DAOException {
		try {
			Session session = HibernateUtil.getSession();
			HibernateUtil.beginTransaction();

			Iterator<E> iter = listObject.listIterator();
			while (iter.hasNext()) {
				session.delete(iter.next());
			}

			HibernateUtil.commitTransaction();

		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			LOGGER.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.fatal(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			HibernateUtil.closeSession();
		}
	}
}