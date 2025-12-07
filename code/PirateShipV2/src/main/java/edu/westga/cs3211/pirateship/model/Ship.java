package edu.westga.cs3211.pirateship.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.westga.cs3211.pirateship.model.serializers.CargoSerializer;
import edu.westga.cs3211.pirateship.model.serializers.TransactionSerializer;

/**
 * Models a pirate ship.
 * @author Justin Smith
 * @version Fall 2025
 */
public class Ship {
	private String name;
	private CargoHull cargoHull;
	private User currentUser;
	private Collection<User> crew;
	
	/**
	 * Instantiates a new ship.
	 *
	 * @param name the name of the ship
	 * @param capacity the capacity of the cargo hull
	 */
	public Ship(String name, int capacity) {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("Name cannot be null or blank.");
		}
		if (capacity <= 0) {
			throw new IllegalArgumentException("Capacity must be greater than zero.");
		}
		this.name = name;
		this.cargoHull = new CargoHull(capacity);
		this.crew = new ArrayList<User>();
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name of the ship
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gets the cargo hull.
	 *
	 * @return the cargo hull
	 */
	public CargoHull getCargoHull() {
		return this.cargoHull;
	}
	
	/**
	 * Sets the cargo hull.
	 *
	 * @param cargoHull the new cargo hull
	 * @throws IllegalArgumentException if the cargo hull is null.
	 */
	public void setCargoHull(CargoHull cargoHull) {
		if (cargoHull == null) {
			throw new IllegalArgumentException("Cargo hull cannot be null.");
		}
		this.cargoHull = cargoHull;
	}
	
	/**
	 * Gets the current user.
	 *
	 * @return the current user
	 */
	public User getCurrentUser() {
		return this.currentUser;
	}
	
	/**
	 * Sets the current user.
	 *
	 * @param user the new current user
	 * @throws IllegalArgumentException if the user is null.
	 */
	public void setCurrentUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException("Current user cannot be null.");
		}
		this.currentUser = user;
	}
	
	/**
	 * Gets the crew.
	 *
	 * @return the crew
	 */
	public Collection<User> getCrew() {
		return this.crew;
	}
	
	/**
	 * Adds a crew member to the ship.
	 *
	 * @param user the user to add as a crew member
	 * @throws IllegalArgumentException if the user is null.
	 */
	public void addCrewMember(User user) {
		if (user == null) {
			throw new IllegalArgumentException("User cannot be null.");
		}
		this.crew.add(user);
	}
	
	/**
	 * Gets the containers in the ship's cargo hull.
	 *
	 * @return the containers in the cargo hull
	 */
	public List<Container> getContainers() {
		return this.cargoHull.getContainers();
	}
	
	/**
	 * Gets the transaction history of the ship's cargo hull.
	 *
	 * @return the transaction history
	 */
	public List<Transaction> getTransactions() {
		return this.cargoHull.getTransactionHistory();
	}
	
	/**
	 * Adds a container to the ship's cargo hull.
	 *
	 * @param container the container to add to the cargo hull
	 * @throws IllegalArgumentException if the container cannot be added.
	 */
	public void addContainer(Container container) {
		if (container == null) {
			System.out.println("Ship Level Null");
			throw new IllegalArgumentException("Container cannot be null.");
		}
		try {
			this.cargoHull.addContainer(container);
			System.out.println("Container added Ship Level: " + container.toString());
		} catch (IllegalArgumentException ex) {
			throw ex;
		}
	}
	
	/**
	 * Adds stock to a container in the ship's cargo hull.
	 *
	 * @param containerId the ID of the container to add stock to
	 * @param stock the stock to add to the container
	 * @throws IllegalArgumentException if the stock cannot be added.
	 */
	public void addStockToContainer(int containerId, Stock stock) {
		if (stock == null) {
			throw new IllegalArgumentException("Stock cannot be null.");
		}
		try {
			this.cargoHull.addStockToContainer(stock, containerId, this.currentUser);
		} catch (IllegalArgumentException ex) {
			throw ex;
		}
	}
	
	/**
	 * Save data.
	 */
	public void saveData() {
		try {
			CargoSerializer.serializeCargoToFile(this.getCargoHull());
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		try {
			TransactionSerializer.serializeTransactionHistoryToFile(this.getCargoHull());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
}
