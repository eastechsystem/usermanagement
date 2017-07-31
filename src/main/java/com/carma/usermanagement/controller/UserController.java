package com.carma.usermanagement.controller;

import java.util.List;

import com.carma.usermanagement.common.AbstractController;
import com.carma.usermanagement.common.ServiceManager;
import com.carma.usermanagement.ejbservice.UserServiceRemote;
import com.carma.usermanagement.model.User;

public class UserController extends AbstractController {
	
	/**
	 * The getAssetTrackingService() method is used to get UserService for
	 * accessing User services (methods)
	 * 
	 * @return UserServiceRemote object
	 */
	protected UserServiceRemote getUserService() {
		return (UserServiceRemote) ServiceManager.getService(UserServiceRemote.class,ServiceManager.APP_NAME_USER_MANAGEMENT);
	}
	
	/**
	 * The getUserBean() method is used to get UserBean
	 * instance from session.
	 * 
	 * @return UserBean instance
	 */
	private UserBean getUserBean() {
		return (UserBean) getControllerObject("userBean", UserBean.class);
	}
	
	public void loadUsers() {
		UserBean userBean = getUserBean();
		try {
			List<User> users = getUserService().getAllUser();
			userBean.setUsers(users);
			System.out.println("Users =====> " + getUserBean().getUsers().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void saveUser() {
		User user = getUserBean().getUser();
		Integer id = getUserService().saveUser(user);
		
		if (null !=id) {
			System.out.println("User saved with id ==> " + id);
			loadUsers();
			getUserBean().clear();
		} else {
			System.out.println("Failed to save user");
		}
	}

	public void updateUser() {
		User user = getUserBean().getSelectedUser();
		Boolean updatedUser = getUserService().updateUser(user);
		
		if (Boolean.TRUE.equals(updatedUser)) {
			System.out.println("User updated successfully");
			loadUsers();
		}
	}

	public void deleteUser() {
		User user = getUserBean().getSelectedUser();
		Boolean deletedUser = getUserService().deleteUser(user);

		if (Boolean.TRUE.equals(deletedUser)) {
			System.out.println("User deleted successfully");
			loadUsers();
		}
	}
}
