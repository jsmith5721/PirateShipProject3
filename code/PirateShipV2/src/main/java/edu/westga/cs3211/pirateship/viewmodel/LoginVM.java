package edu.westga.cs3211.pirateship.viewmodel;

import edu.westga.cs3211.pirateship.model.Authenticator;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.model.serializers.UserSerializer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;

/**
 * The Class LoginVM.
 * @author Justin Smith
 * @version Fall 2025
 */
public class LoginVM {
    private Ship ship;
    private final StringProperty username;
    private final StringProperty password;

    /**
     * Instantiates a new login VM.
     */
    public LoginVM() {
    	this.ship = new Ship("Flying Duchman", 5000);
        this.username = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");

        try {
            UserSerializer.loadUsers(this.ship);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Username property.
     *
     * @return the string property
     */
    public StringProperty usernameProperty() {
        return this.username;
    }

    /**
     * Password property.
     *
     * @return the string property
     */
    public StringProperty passwordProperty() {
        return this.password;
    }
    
    /**
     * Get's the ship.
     * 
     * @return the ship
     */
    public Ship getShip() {
		return this.ship;
	}

    /**
     * Login.
     *
     * @return the user
     */
    public boolean login() {
        String user = this.username.get();
        String pass = this.password.get();
        if (user == null || user.isBlank() || pass == null || pass.isBlank()) {
            throw new IllegalArgumentException("Username and password cannot be empty.");
        }
        User authenticatedUser = Authenticator.authenticate(user, pass, this.ship.getCrew());
        if (authenticatedUser != null) {
			this.ship.setCurrentUser(authenticatedUser);
			return true;
		} else {
			return false;
		}
    }
    
    /**
     * Save users.
     */
    public void saveUsers() {
		try {
			UserSerializer.saveUsers(this.ship);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
