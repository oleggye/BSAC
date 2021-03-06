package by.bsac.timetable.hibernateFiles;

import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import by.bsac.timetable.exception.ApplicationException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author hello
 */
public class HibernateUtil {

	private static final SessionFactory sessionFactory;

	private static final ThreadLocal<Session> threadSession = new ThreadLocal<>();
	private static final ThreadLocal<Transaction> threadTransaction = new ThreadLocal<>();

	static {
		try {
			// Create the SessionFactory from standard (hibernate.cfg.xml)
			// config file.
			Configuration configuration = new Configuration().configure();
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();

			// builds a session factory from the service registry
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (Throwable ex) {
			// Log the exception.
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ApplicationException("База данных не доступна!", ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static Session getSession() throws HibernateException {
		Session s = threadSession.get();
		// Open a new Session, if this thread has none yet
		if (s == null) {
			s = sessionFactory.openSession();
			threadSession.set(s);
		}
		return s;
	}

	public static void closeSession() {
		Session s = threadSession.get();
		threadSession.set(null);
		if (s != null && s.isOpen())
			s.close();
	}

	public static void beginTransaction() {
		Transaction tx = threadTransaction.get();
		if (tx == null) {
			tx = getSession().beginTransaction();
			threadTransaction.set(tx);
		}
	}

	public static void commitTransaction() {
		Transaction tx = threadTransaction.get();
		if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack())
			tx.commit();
		threadTransaction.set(null);
	}

	public static void rollbackTransaction() {
		Transaction tx = threadTransaction.get();
		try {
			threadTransaction.set(null);
			if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
				tx.rollback();
			}
		} finally {
			closeSession();
		}
	}
}