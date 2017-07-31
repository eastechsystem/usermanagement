package com.carma.usermanagement.ejbservice;

import java.util.List;

import javax.ejb.Remote;

import com.carma.usermanagement.model.User;

/**
 * @author Jahanzaib Ali
 * @since July 31, 2017
 *
 */
@Remote
public interface UserServiceRemote {

	public List<User> getAllUser() throws Exception;

	public User getUserById(Integer userId) throws Exception;

	public Integer saveUser(User user);

	public Boolean updateUser(User user);

	public Boolean deleteUser(User user);

}
