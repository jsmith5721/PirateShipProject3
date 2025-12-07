package edu.westga.cs3211.pirateship.model;

/**
 * Models a user in the pirate ship management system.
 * @author Justin Smith
 * @version Fall 2025
 */
public class User {
	private String name;
	private String username;
	private String password;
	private Roles role;
	
	/**
	 * Instantiates a new user.
	 *
	 * @param name the name of the user
	 * @param username the username of the user
	 * @param password the password of the user
	 * @param role the role of the user
	 */
	public User(String name, String username, String password, Roles role) {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("Name cannot be null or blank");
		}
		if (username == null || username.isBlank()) {
			throw new IllegalArgumentException("Username cannot be null or blank");
		}
		if (password == null || password.isBlank()) {
			throw new IllegalArgumentException("Password cannot be null or blank");
		}
		if (role == null) {
			throw new IllegalArgumentException("Role cannot be null");
		}
		this.name = name;
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	/**
	 * Gets the name of the user.
	 *
	 * @return the name of the user
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gets the username of the user.
	 *
	 * @return the username of the user
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Gets the password of the user.
	 *
	 * @return the password of the user
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * Gets the role of the user.
	 *
	 * @return the role of the user
	 */
	public Roles getRole() {
		return this.role;
	}
}
