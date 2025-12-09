package edu.westga.cs3211.pirateship.model.serializers;

import java.util.List;

import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.Transaction;

/**
 * The Class ShipSerializer.
 * @author Justin Smith
 * @version Fall 2025
 */
public class ShipSerializer {
	public static final String USERS_TXT_FILE = "users.txt";
	public static final String TRANSACTION_TXT_FILE = "transactionHistory.txt";
	public static final String CARGO_TXT_FILE = "cargo.txt";
	
	/**
	 * Save ship.
	 *
	 * @param ship the ship
	 * @param usersFile the file to save users to
	 * @param cargoFile the file to save cargo to
	 * @param transactionFile the file to save transactions to
	 */
	public static void saveShip(Ship ship, String usersFile, String cargoFile, String transactionFile) {
		UserSerializer.saveUsers(ship, usersFile);
				
		CargoSerializer.saveCargo(ship, cargoFile);
		
		TransactionSerializer.saveTransactionHistory(ship, transactionFile);
	}
	
	/**
	 * Load ship.
	 *
	 * @param usersFile the file to load users from
	 * @param cargoFile the file to load cargo from
	 * @param transactionFile the file to load transactions from
	 * @return the ship
	 */
	public static Ship loadShip(String usersFile, String cargoFile, String transactionFile) {
		Ship loadedShip = CargoSerializer.loadCargo(cargoFile);

        UserSerializer.loadUsers(loadedShip, usersFile);
		 
        List<Transaction> transactions = TransactionSerializer.loadTransactionHistory(transactionFile);
        if (transactions != null) {
            loadedShip.getCargoHull().getTransactionHistory().addAll(transactions);
        }
        
        return loadedShip;
	}
}
