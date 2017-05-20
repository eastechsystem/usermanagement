/**
 * 
 */
package com.topcareer.usermanagement.infrastructure;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.topcareer.usermanagement.ws.UserResource;

/**
 * The RestInit class is used to initialize the web resources.
 * 
 * @author Jahanzaib Ali
 * @since May 21, 2017
 */

@ApplicationPath("/")
public class RestInit extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	public RestInit() {
		singletons.add(new UserResource());
	}

	@Override
	public Set<Class<?>> getClasses() {
		return empty;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
