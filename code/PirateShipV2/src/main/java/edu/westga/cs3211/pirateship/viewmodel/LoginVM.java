package edu.westga.cs3211.pirateship.viewmodel;

import edu.westga.cs3211.pirateship.model.Authenticator;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.model.serializers.ShipSerializer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
    	this.ship = ShipSerializer.loadShip(ShipSerializer.USERS_TXT_FILE, ShipSerializer.CARGO_TXT_FILE, ShipSerializer.TRANSACTION_TXT_FILE);
    	
        this.username = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");
    }
    
    /**
     * FOR TESTING PURPOSES ONLY
     * Instantiates a new login VM.
     * 
     * @param ship the ship
     */
    public LoginVM(Ship ship) {
    	this.ship = ship;
    	
        this.username = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");
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
    public void saveData() {
    	this.ship.saveShipData(ShipSerializer.USERS_TXT_FILE, ShipSerializer.CARGO_TXT_FILE, ShipSerializer.TRANSACTION_TXT_FILE);
	}
}
