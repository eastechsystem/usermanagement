package com.carma.usermanagement.controller;

import java.io.Serializable;
import java.util.List;

import com.carma.usermanagement.model.User;

public class UserBean extends UserController implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<User> users;
	private User user = new User();
	private User selectedUser;
	/**
	 * @return the users
	 */
	public List<User> getUsers() {
		return users;
	}
	/**
	 * @param users the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the selectedUser
	 */
	public User getSelectedUser() {
		return selectedUser;
	}
	/**
	 * @param selectedUser the selectedUser to set
	 */
	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}
	
	public void clear() {
		this.user = new User();
	}
}
