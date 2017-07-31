package com.carma.usermanagement.common;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.carma.usermanagement.util.HibernateUtil;

/**
 * The AbstractHibernateDao class handles core database operations.
 * 
 */
public abstract class AbstractHibernateDao<T, ID extends Serializable> implements GenericDao<T, ID> {

	private Transaction transaction = null;

	public Session getSession() {
		try {
			Session session = HibernateUtil.openSession();
			transaction = session.beginTransaction();
			return session;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void closeSession(Session session) {
		try {
			if (session != null) {
				if (!transaction.wasCommitted()) {
					transaction.commit();
				}

				HibernateUtil.closeSession(session);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// ******************************** Generics override methods ***************************//

	@Override
	public Integer saveRecord(T clazz) {
		Session session = getSession();
		try {
			Integer id = (Integer) session.save(clazz);
			System.err.println(clazz);
			return id;
		} catch (HibernateException ex) {
			throw ex;
		} finally {
			closeSession(session);
		}
	}

	@Override
	public Boolean deleteRecord(T clazz) {
		boolean isDelete = false;
		Session session = getSession();
		try {
			session.delete(clazz);
			isDelete = true;
		} catch (HibernateException ex) {
			throw ex;
		} finally {
			closeSession(session);
		}
		return isDelete;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAllRecord(Class<? extends Object> class1) {
		Session session = getSession();
		List<T> records = null;
		try {
			Criteria cr = session.createCriteria(class1);
			records = cr.list();

		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return records;

	}

	@Override
	public Boolean updateRecord(T clazz) {
		Session session = getSession();
		boolean isUpdate = false;
		try {
			session.saveOrUpdate(clazz);
			isUpdate = true;
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			closeSession(session);
		}

		return isUpdate;
	}

	@Override
	public Boolean deleteRecord(Class<T> clazz, ID id) {
		boolean isDelete = false;
		Session session = getSession();
		try {
			Object record = session.get(clazz, id);
			session.delete(record);
			isDelete = true;
		} catch (HibernateException ex) {
			throw ex;
		} finally {
			closeSession(session);
		}
		return isDelete;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findById(Class<T> clazz, ID id) {
		if (id == null) {
			throw new IllegalArgumentException("null id cannot be retrieved");
		}
		if (clazz == null) {
			throw new IllegalArgumentException("Persistent object type cannot be null");
		}

		Session session = getSession();

		try {
			return (T) session.get(clazz, id);
		} catch (HibernateException ex) {
			throw ex;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T merge(T instance) {
		if (instance == null) {
			throw new IllegalArgumentException("Instance cannot be null");
		}
		Session session = getSession();
		T instance2 = null;

		try {
			instance2 = (T) session.merge(instance);
			session.flush();
		} catch (HibernateException ex) {
			throw ex;
		}

		return instance2;
	}

}
