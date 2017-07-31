package com.carma.usermanagement.ejbservice;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.carma.usermanagement.common.DaoManager;
import com.carma.usermanagement.dao.UserDao;
import com.carma.usermanagement.model.User;

/**
 * @author Jahanzaib Ali
 * @since July 31, 2017
 *
 */
@Stateless
public class UserService implements UserServiceRemote {

	/**
	 * TODO Add method doc
	 *
	 * @return UserDao
	 */
	private UserDao getUserDao() {
		return (UserDao) DaoManager.getInstance().getDao(UserDao.class.getName());
	}

	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<User> getAllUser() {
		try {
			return getUserDao().getAllRecord(User.class);
		} catch (RuntimeException aex) {
			throw new EJBException(aex);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public User getUserById(Integer userId) {
		try {
			return getUserDao().findById(User.class, userId);
		} catch (RuntimeException aex) {
			throw new EJBException(aex);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Integer saveUser(User user) {
		try {
			return getUserDao().saveRecord(user);
		} catch (RuntimeException aex) {
			throw new EJBException(aex);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Boolean updateUser(User user) {
		try {
			return getUserDao().updateRecord(user);
		} catch (RuntimeException aex) {
			throw new EJBException(aex);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Boolean deleteUser(User user) {
		try {
			return getUserDao().deleteRecord(user);
		} catch (RuntimeException aex) {
			throw new EJBException(aex);
		}
	}

}
