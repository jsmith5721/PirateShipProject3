package edu.westga.cs3211.pirateship.viewmodel;

import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.model.serializers.ShipSerializer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;

/**
 * The Class LandingPageVM.
 * 
 * @author Justin Smith
 * @version Fall 2025
 */
public class LandingPageVM {
	private Ship ship;
	private final StringProperty welcomeMessage;
	private final BooleanProperty canReviewStockChanges;

	/**
	 * Instantiates a new landing page VM.
	 * 
	 * @param currentUser the currently logged in user
	 */
	public LandingPageVM(User currentUser) {
		try {
			this.ship = ShipSerializer.loadShip();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		this.ship.setCurrentUser(currentUser);

		this.welcomeMessage = new SimpleStringProperty("Welcome, " + this.ship.getCurrentUser().getName());
		this.canReviewStockChanges = new SimpleBooleanProperty(this.ship.getCurrentUser().getRole() == Roles.QUARTERMASTER);

	}

	/**
	 * Gets the ship.
	 *
	 * @return the ship
	 */
	public Ship getShip() {
		return this.ship;
	}

	/**
	 * Welcome message property.
	 *
	 * @return the string property
	 */
	public StringProperty welcomeMessageProperty() {
		return this.welcomeMessage;
	}

	/**
	 * Can review stock changes property.
	 *
	 * @return the boolean property
	 */
	public BooleanProperty canReviewStockChangesProperty() {
		return this.canReviewStockChanges;
	}

	/**
	 * Save data.
	 */
	public void saveData() {
		try {
			ShipSerializer.saveShip(this.ship, ShipSerializer.USERS_TXT_FILE, ShipSerializer.CARGO_TXT_FILE,
					ShipSerializer.TRANSACTION_TXT_FILE);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
