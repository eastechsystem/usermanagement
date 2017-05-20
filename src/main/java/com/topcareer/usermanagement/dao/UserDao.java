package com.topcareer.usermanagement.dao;

import java.util.List;

import com.topcareer.usermanagement.model.User;

/**
 * @author Jahanzaib Ali
 * @since May 21, 2017
 *
 */
public interface UserDao {

	public List<User> getAllUser() throws Exception;

	public User getUserById(Integer notificationId) throws Exception;
}
