package com.carma.usermanagement.util;

import java.net.URL;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * The HibernateUtil class provides utility operations for hibernate framework
 */
public class HibernateUtil {

	private static SessionFactory sessionFactory = null;
	private static ServiceRegistry serviceRegistry = null;

	/**
	 * This method returns Hibernate session factory.
	 * 
	 * @return Hibernate session factory.
	 */
	public static SessionFactory getSessionFactory() {

		if (sessionFactory == null) {
			try {
				URL cfgUrl = HibernateUtil.class.getClassLoader().getResource("hibernate.cfg.xml");
				sessionFactory = buildSessionFactory(cfgUrl);
			} catch (Exception ex) {
				throw new ExceptionInInitializerError(ex);
			}
		}
		return sessionFactory;
	}

	/**
	 * This method returns Hibernate session factory from given configurations.
	 * 
	 * @param cfgUrl
	 *            Specifies hibernate.cfg.xml file.
	 * @return Hibernate session factory created from given configurations.
	 */
	private static SessionFactory buildSessionFactory(URL cfgUrl) {

		Configuration configuration = new Configuration();
		configuration.configure(cfgUrl);

		serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		return sessionFactory;
	}

	/**
	 * This method returns a Hibernate session object.
	 * 
	 * @return Hibernate session object.
	 */
	public static Session openSession() {
		return getSessionFactory().openSession();
	}

	/**
	 * This method closes the given Hibernate session.
	 * 
	 * @param session
	 *            Specifies Hibernate session to be closed.
	 */
	public static void closeSession(Session session) {
		if (session.isOpen()) {
			session.close();
		}
	}

	/**
	 * This method is used to get hibernate session for a JTA transaction.
	 * 
	 * @return Hibernate session for a JTA transaction
	 */
	public static Session getCurrentSession() {
		Session session = null;
		if (getSessionFactory().getCurrentSession() != null) {
			session = getSessionFactory().getCurrentSession();
		} else if (session == null || session.isOpen() == false) {
			session = getSessionFactory().openSession();
		}

		return session;
	}

}
