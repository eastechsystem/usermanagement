package com.topcareer.usermanagement.ejbservice;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.topcareer.usermanagement.common.DaoManager;
import com.topcareer.usermanagement.dao.UserDao;
import com.topcareer.usermanagement.model.User;

/**
 * @author Jahanzaib Ali
 * @since May 21, 2017
 *
 */
@Stateless
public class UserService implements UserServiceRemote {

	/**
	 * TODO Add method doc
	 *
	 * @return UserDao
	 */
	private UserDao getNotificationDao() {
		return (UserDao) DaoManager.getInstance().getDao(UserDao.class.getName());
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<User> getAllUser() throws Exception {
		try {
			return getNotificationDao().getAllUser();
		} catch (RuntimeException aex) {
			throw new EJBException(aex);
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public User getUserById(Integer userId) throws Exception {
		try {
			return getNotificationDao().getUserById(userId);
		} catch (RuntimeException aex) {
			throw new EJBException(aex);
		}
	}

}
