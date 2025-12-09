package edu.westga.cs3211.pirateship.viewmodel;

import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.model.serializers.ShipSerializer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
		this.ship = ShipSerializer.loadShip(ShipSerializer.USERS_TXT_FILE, ShipSerializer.CARGO_TXT_FILE, ShipSerializer.TRANSACTION_TXT_FILE);

		this.ship.setCurrentUser(currentUser);

		this.welcomeMessage = new SimpleStringProperty("Welcome, " + this.ship.getCurrentUser().getName());
		this.canReviewStockChanges = new SimpleBooleanProperty(this.ship.getCurrentUser().getRole() == Roles.QUARTERMASTER);
	}
	
	/**
	 * FOR TESTING PURPOSES ONLY
	 * Instantiates a new landing page VM.
	 * 
	 * @param currentUser the currently logged in user
	 * @param ship        the ship
	 */
	public LandingPageVM(User currentUser, Ship ship) {
		this.ship = ship;

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
		this.ship.saveShipData(ShipSerializer.USERS_TXT_FILE, ShipSerializer.CARGO_TXT_FILE, ShipSerializer.TRANSACTION_TXT_FILE);
	}
}
