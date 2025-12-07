package edu.westga.cs3211.pirateship.viewmodel;

import edu.westga.cs3211.pirateship.model.CargoHull;
import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.Transaction;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.model.serializers.CargoSerializer;
import edu.westga.cs3211.pirateship.model.serializers.TransactionSerializer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.util.List;

/**
 * The Class LandingPageVM.
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
     * @param ship the ship being modeled
     */
    public LandingPageVM(Ship ship) {
		this.ship = ship;

		this.welcomeMessage = new SimpleStringProperty("Welcome, " + ship.getCurrentUser().getName());
		this.canReviewStockChanges = new SimpleBooleanProperty(
				ship.getCurrentUser().getRole() == Roles.QUARTERMASTER
		);
		
		this.loadCargoAndTransactions();
	}

    /**
     * Load cargo and transactions.
     */
    private void loadCargoAndTransactions() {
        try {
        	CargoHull hull = CargoSerializer.loadCargo();
            if (hull != null) {
            	this.ship.setCargoHull(hull);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            List<Transaction> transactions = TransactionSerializer.loadTransactionHistory();
            if (transactions != null) {
            	this.ship.getCargoHull().getTransactionHistory().addAll(transactions);
            }
        } catch (IOException ex) {
        	ex.printStackTrace();
        }
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
     * Gets the current user.
     *
     * @return the current user
     */
    public User getCurrentUser() {
        return this.ship.getCurrentUser();
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
		this.ship.saveData();
	}
}
