package com.topcareer.usermanagement.ejbservice;

import java.util.List;

import javax.ejb.Remote;

import com.topcareer.usermanagement.model.User;

/**
 * @author Jahanzaib Ali
 * @since May 21, 2017
 *
 */
@Remote
public interface UserServiceRemote {

	public List<User> getAllUser() throws Exception;

	public User getUserById(Integer userId) throws Exception;
}
