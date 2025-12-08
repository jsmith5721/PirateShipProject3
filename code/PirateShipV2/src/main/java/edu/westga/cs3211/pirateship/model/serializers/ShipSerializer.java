package edu.westga.cs3211.pirateship.model.serializers;

import java.io.IOException;
import java.util.List;

import edu.westga.cs3211.pirateship.model.CargoHull;
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
	 * @throws IOException 
	 */
	public static void saveShip(Ship ship, String usersFile, String cargoFile, String transactionFile) throws IOException {
		try {
			UserSerializer.saveUsers(ship, usersFile);
		} catch (IOException ex) {
			throw ex;
		}
		
		try {
			CargoSerializer.saveCargo(ship.getCargoHull(), cargoFile);
		} catch (IOException ex) {
			throw ex;
		}

		try {
			TransactionSerializer.saveTransactionHistory(ship.getCargoHull(), transactionFile);
		} catch (IOException ex) {
			throw ex;
		}
	}
	
	/**
	 * Load ship.
	 *
	 * @return the ship
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Ship loadShip() throws IOException {
		Ship ship = new Ship("Flying Duchman", 5000);
		
		try {
            UserSerializer.loadUsers(ship);
        } catch (IOException ex) {
            throw ex;
        }
		
		try {
        	CargoHull hull = CargoSerializer.loadCargo();
            if (hull != null) {
            	ship.setCargoHull(hull);
            }
        } catch (IOException ex) {
            throw ex;
        }

        try {
            List<Transaction> transactions = TransactionSerializer.loadTransactionHistory();
            if (transactions != null) {
            	ship.getCargoHull().getTransactionHistory().addAll(transactions);
            }
        } catch (IOException ex) {
        	throw ex;
        }
        
        return ship;
	}
}
