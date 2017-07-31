package com.carma.usermanagement.common;

import java.util.HashMap;
import java.util.Map;

import com.carma.usermanagement.dao.UserDao;
import com.carma.usermanagement.dao.UserDaoHibernateImpl;

/**
 * @author Jahanzaib Ali
 * @since July 31, 2017
 *
 */
public class DaoManager {
	/** Singleton instance variable name */
	private static final DaoManager INSTANCE;

	/** Map holding all implementation daos */
	private static Map<String, Object> daoMap = new HashMap<String, Object>();

	/**
	 * Static block to create singleton instance
	 */
	static {
		try {
			INSTANCE = new DaoManager();
			loadInitializeDaoInstances();
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * Empty constructor: we don't want to take its direct objects
	 */
	private DaoManager() {
	}

	/**
	 * Singleton method creates single instance of DaoManager
	 * 
	 * @return DaoManager instance.
	 */
	public static DaoManager getInstance() {
		return INSTANCE;
	}

	private static void loadInitializeDaoInstances() {
		daoMap = new HashMap<String, Object>(0);
		daoMap.put(UserDao.class.getName(), new UserDaoHibernateImpl());
	}

	/**
	 * getDao() method is used to get dao instance by dao class name
	 * 
	 * @param daoName
	 * @return daoObject
	 */
	public Object getDao(String daoName) {
		if (daoMap == null) {
			loadInitializeDaoInstances();
		}

		return daoMap.get(daoName);
	}
}
