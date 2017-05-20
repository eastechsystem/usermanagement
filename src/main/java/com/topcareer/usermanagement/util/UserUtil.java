package com.topcareer.usermanagement.util;

import java.io.StringReader;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import com.topcareer.usermanagement.model.User;

/**
 * @author Jahanzaib Ali
 * @since May 21, 2017
 *
 */
public class UserUtil {

	/**
	 * The getPopulatedUserJsonObject() method is use to convert user into json
	 * object of JsonObject type
	 * 
	 * @param user
	 *            specifies the User object
	 * @return json object
	 */
	public static JsonObject getPopulatedUserJsonObject(User user) {
		System.out.println("getPopulatedUserJsonObject() method starts!");

		JsonObjectBuilder request = Json.createObjectBuilder();
		try {
			request.add(UserConstants.JSON_KEY_USER_ID, user.getId());
			request.add(UserConstants.JSON_KEY_USER_NAME, user.getName());

			JsonArrayBuilder builder = Json.createArrayBuilder();
			for (Integer friend : user.getFriends()) {
				if (null != friend) {
					builder.add(friend.intValue());
				}

			}
			JsonArray friendJsonArray = builder.build();

			request.add(UserConstants.JSON_KEY_USER_FRIENDS, friendJsonArray);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("getPopulatedUserJsonObject() method ends!");

		return request.build();
	}

	/**
	 * The getPopulatedUsersJsonArray() method is use to convert users into
	 * jsonArray of of String type
	 * 
	 * @param users
	 *            specifies the user object
	 * @return jsonArray of String type object
	 */
	public static String getPopulatedUsersJsonArray(List<User> users) {
		System.out.println("getPopulatedUsersJsonArray() method starts!");

		JsonObjectBuilder response = Json.createObjectBuilder();
		String jsonDetail = "";

		try {
			JsonArrayBuilder usersJsonArray = Json.createArrayBuilder();

			for (User user : users) {
				usersJsonArray.add(getPopulatedUserJsonObject(user));
			}

			// final json response populated
			response.add(UserConstants.JSON_KEY_DETAIL, usersJsonArray);
			jsonDetail = response.build().toString();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("getPopulatedUsersJsonArray() method ends!");

		return jsonDetail;
	}

	/**
	 * The isNumber() method returns true if the given string is a number.
	 * Otherwise it returns false.
	 * 
	 * @param string
	 *            Specifies the element that is to be checked for number type
	 * @return Returns true if the given string is a number. Otherwise it
	 *         returns false.
	 */
	public static boolean isNumber(String string) {
		boolean flag = false;
		if (null != string && string.trim().length() > 0) {
			try {
				Double.parseDouble(string);
				flag = true;
			} catch (NumberFormatException nfe) {
			}
		}
		return flag;
	}

	/**
	 * The getStatusMessageByStatus() method is used to return the json format
	 * status message by status
	 * 
	 * @param status
	 *            Specifies the status of String type
	 * @return Returns JsonObject.
	 */
	public static JsonObject getStatusMessageByStatus(String status) {
		JsonObject responseJsonObject = null;

		if (UserConstants.FAILURE.equalsIgnoreCase(status)) {
			JsonObjectBuilder response = Json.createObjectBuilder();
			response.add(UserConstants.ERROR_MESSAGE, status);
			response.add(UserConstants.ERROR_CODE, UserConstants.BAD_REQUEST_CODE);
			responseJsonObject = Json.createReader(new StringReader(response.build().toString())).readObject();
		}

		return responseJsonObject;
	}
}
