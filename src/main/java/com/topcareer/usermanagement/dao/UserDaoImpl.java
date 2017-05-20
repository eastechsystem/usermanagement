package com.topcareer.usermanagement.dao;

import java.util.List;

import com.topcareer.usermanagement.model.User;
import com.topcareer.usermanagement.util.FileUtil;

/**
 * @author Jahanzaib Ali
 * @since May 21, 2017
 *
 */
public class UserDaoImpl implements UserDao {

	@Override
	public List<User> getAllUser() throws Exception {
		try {
			List<User> user = FileUtil.getAllUserFromUsersDataTextFile();
			return user;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public User getUserById(Integer userId) throws Exception {
		try {
			List<User> users = getAllUser();
			for (User user : users) {
				if (userId.equals(user.getId())) {
					return user;
				}
			}

		} catch (Exception e) {
			throw e;
		}

		return null;
	}

}
