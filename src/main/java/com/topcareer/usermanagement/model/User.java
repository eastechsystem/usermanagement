/**
 * 
 */
package com.topcareer.usermanagement.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jahanzaib Ali
 * @since May 21, 2017
 */
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Integer id;
	String name;
	List<Integer> friends;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the friends
	 */
	public List<Integer> getFriends() {
		return friends;
	}

	/*
	 * @param friends2
	 *            the friends to set
	 */
	public void setFriends(List<Integer> friends2) {
		this.friends = friends2;
	}
}
