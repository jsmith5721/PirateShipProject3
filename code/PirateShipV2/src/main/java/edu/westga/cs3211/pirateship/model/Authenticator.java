package edu.westga.cs3211.pirateship.model;

import java.util.Collection;

/**
 * Authenticator class to manage user authentication.
 *
 * @author Justin Smith
 * @version Fall 2025
 */
public class Authenticator {
	/**
	 * Authenticate a user by username and password.
	 *
	 * @param username      the user's username
	 * @param password      the user's password
	 * @param users         the list of users to authenticate against
	 * @return User object if authentication is successful, null otherwise
	 * @throws IllegalArgumentException if username or password is null or empty
	 */
	public static User authenticate(String username, String password, Collection<User> users) {
		if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
			throw new IllegalArgumentException("Username and password cannot be empty");
		}
		if (users == null) {
			throw new IllegalArgumentException("Users collection cannot be null");
		}
		for (User user : users) {
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				return user;
			}
		}
		return null;
	}
}
