package com.topcareer.usermanagement.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.topcareer.usermanagement.model.User;

/**
 * @author Jahanzaib Ali
 * @since May 21, 2017
 *
 */
public class FileUtil {
	private static final String USERS_DATA_FILE_PATH = "snippet/usersdata.txt";
	private static List<User> users = null;

	static {
		try {
			loadUsersFromFileText();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void loadUsersFromFileText() {
		BufferedReader br = null;
		users = new ArrayList<User>();

		try {
			URL url = FileUtil.class.getClassLoader().getResource(USERS_DATA_FILE_PATH);
			br = new BufferedReader(new FileReader(url.getPath()));

			String fetchedFileRow;
			Boolean skipFirstRowInFile = false;

			while (null != (fetchedFileRow = br.readLine())) {
				if (Boolean.FALSE.equals(skipFirstRowInFile)) {
					skipFirstRowInFile = true;
					continue;
				}
				User user = getPopulatedUserData(fetchedFileRow);

				if (null != user) {
					users.add(user);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != br)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * getUserFromUserDataTextFile() this method is used to fetch all users data
	 * from usersdata.txt file
	 * 
	 * @return
	 */
	public static List<User> getAllUserFromUsersDataTextFile() {
		if (null == users || users.size() < 1) {
			loadUsersFromFileText();
		}

		return users;
	}

	private static User getPopulatedUserData(String fetchedFileRow) {

		try {
			// file formated each columns by 3 tabs so used regex to split each
			// column
			String[] arr = fetchedFileRow.split("[\\t]{3}");

			if (arr.length < 1){
				return null;
			}
			
			//userId
			Integer userId = null;
			if (null != arr[0] || !"".equals(arr[0])) {
				try {
					userId = Integer.parseInt(arr[0].trim());
				} catch (NumberFormatException nex) {
					nex.printStackTrace();
				}
			}
			
			//User's friends
			List<Integer> friends = null;
			if (arr.length > 2 && (null != arr[2] || !"".equals(arr[2]))) {
				friends = new ArrayList<Integer>(0);

				String[] frinedIds = arr[2].split(",");
				for (String friendId : frinedIds) {
					try {
						friends.add(Integer.parseInt(friendId.trim()));
					} catch (NumberFormatException nex) {
						System.out.println(nex.getMessage());
					}
				}
			}
			
			//user name
			String name = arr[1].length() > 1 ? arr[1] : "NILL";

			User user = new User();
			user.setId(userId);
			user.setName(name);
			user.setFriends(friends);

			return user;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return null;
	}
}
