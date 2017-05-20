package com.topcareer.usermanagement.ws;

import java.io.StringReader;
import java.util.Date;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.topcareer.usermanagement.common.ServiceManager;
import com.topcareer.usermanagement.ejbservice.UserServiceRemote;
import com.topcareer.usermanagement.model.User;
import com.topcareer.usermanagement.util.UserConstants;
import com.topcareer.usermanagement.util.UserUtil;

/**
 * The UserResource class is used to Represent the user and friends
 * relationship.
 * 
 * @author Jahanzaib Ali
 * @since May 21, 2017
 */
@Path("/api/v1.0")
public class UserResource {
	
	private static JsonObject STATUS_MESSAGE = null;
	static {
		try {
			STATUS_MESSAGE = UserUtil.getStatusMessageByStatus(UserConstants.FAILURE);
		} catch (Exception e) {

		}
	}

	/**
	 * The getUserService() method is used to get UserService for accessing
	 * Users related services (methods)
	 *
	 * @return NotificationServiceRemote object
	 */
	private UserServiceRemote getUserService() {
		return (UserServiceRemote) ServiceManager.getService(UserServiceRemote.class, ServiceManager.APP_NAME_USER_MANAGEMENT);
	}

	/**
	 * sayHello() is a test method to test whether Web service is accessible or
	 * not
	 * 
	 * @return response string
	 */
	@GET
	@Produces("text/plain")
	@Path("/hello")
	public Response sayHello() {
		String result = "Hello World! This is UserManagement Web Service v1.0 (" + (new Date()).toString() + ").";
		// return HTTP response OK(200) in case of success
		return Response.status(Status.OK).entity(result).build();
	}

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/users/")
	public Response getAllUserDetails(@Context HttpHeaders headers) {
		try {
			List<User> users = getUserService().getAllUser();

			String jsonDetail = UserUtil.getPopulatedUsersJsonArray(users);
			JsonObject responseJsonObject = Json.createReader(new StringReader(jsonDetail)).readObject();

			// return HTTP response OK(200) in case of success
			return Response.status(Status.OK).entity(responseJsonObject).build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// return HTTP response BadRequest(400) in case of failure
		return Response.status(Status.BAD_REQUEST).entity(STATUS_MESSAGE).build();
	}
	
	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/users/{identifier}")
	public Response getUserByIdentifier(@Context HttpHeaders headers, @PathParam("identifier") String identifier) {
		try {

			User user = null;
			if (UserUtil.isNumber(identifier)) {
				user = getUserService().getUserById(Integer.parseInt(identifier));
			}

			if (null == user) {
				return Response.status(Status.BAD_REQUEST).entity(STATUS_MESSAGE).build();
			}

			String userJsonObject = UserUtil.getPopulatedUserJsonObject(user).toString();
			JsonObject responseJsonObject = Json.createReader(new StringReader(userJsonObject)).readObject();

			// return HTTP response OK(200) in case of success
			return Response.status(Status.OK).entity(responseJsonObject).build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// return HTTP response BadRequest(400) in case of failure
		return Response.status(Status.BAD_REQUEST).entity(STATUS_MESSAGE).build();
	}
}
