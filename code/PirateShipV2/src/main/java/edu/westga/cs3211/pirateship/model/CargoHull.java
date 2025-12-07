package edu.westga.cs3211.pirateship.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Models the cargo hull of a pirate ship.
 * 
 * @author Justin Smith
 * @version Fall 2025
 */
public class CargoHull {
	private int capacity;
	private List<Container> containers;
	private List<Transaction> transactionHistory;

	/**
	 * Instantiates a new cargo hull.
	 *
	 * @param capacity the capacity of the cargo hull
	 */
	public CargoHull(int capacity) {
		if (capacity <= 0) {
			throw new IllegalArgumentException("Capacity must be greater than zero.");
		}
		this.capacity = capacity;
		this.containers = new ArrayList<Container>();
		this.transactionHistory = new ArrayList<Transaction>();
	}

	/**
	 * Gets the capacity of the cargo hull.
	 *
	 * @return the capacity of the cargo hull
	 */
	public int getCapacity() {
		return this.capacity;
	}

	/**
	 * Gets the containers in the cargo hull.
	 *
	 * @return the containers in the cargo hull
	 */
	public List<Container> getContainers() {
		return this.containers;
	}

	/**
	 * Gets the transaction history of the cargo hull.
	 *
	 * @return the transaction history of the cargo hull
	 */
	public List<Transaction> getTransactionHistory() {
		return this.transactionHistory;
	}

	/**
	 * Gets the current load of the cargo hull.
	 *
	 * @return the current load of the cargo hull
	 */
	public int getCurrentLoad() {
		int currentLoad = 0;
		for (Container container : this.containers) {
			currentLoad += container.calculateCurrentLoad();
		}
		return currentLoad;
	}

	/**
	 * Gets the available capacity of the cargo hull.
	 *
	 * @return the available capacity of the cargo hull
	 */
	public int getAvailableCapacity() {
		return this.capacity - this.getCurrentLoad();
	}

	private Container getContainerById(int containerId) {
		for (Container container : this.containers) {
			if (container.getContainerID() == containerId) {
				return container;
			}
		}
		return null;
	}

	/**
	 * Adds the container.
	 *
	 * @param container the container
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void addContainer(Container container) throws IllegalArgumentException {
		if (container == null) {
			throw new IllegalArgumentException("Container cannot be null.");
		}
		if (this.getAvailableCapacity() >= container.getSize()) {
			this.containers.add(container);
			System.out.println("Container added Hull Level: " + container.toString());
		} else {
			throw new IllegalArgumentException("Not enough capacity in the cargo hull for this container.");
		}
	}

	/**
	 * Adds the stock to container.
	 *
	 * @param stock       the stock
	 * @param containerId the container id
	 * @param crewmember  the crewmember
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void addStockToContainer(Stock stock, int containerId, User crewmember) throws IllegalArgumentException {
		if (stock == null) {
			throw new IllegalArgumentException("Stock cannot be null.");
		}
		if (crewmember == null) {
			throw new IllegalArgumentException("Crewmember cannot be null.");
		}
		Container container = this.getContainerById(containerId);
		if (container == null) {
			throw new IllegalArgumentException("Container with ID " + containerId + " not found.");
		}
		try {
			Transaction transcation = container.addStockItem(stock, crewmember);
			if (transcation != null) {
				this.transactionHistory.add(transcation);
			}
		} catch (IllegalArgumentException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
	}

}
