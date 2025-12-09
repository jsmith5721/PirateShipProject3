package edu.westga.cs3211.pirateship.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.NoSuchElementException;

/**
 * Models a container that holds stock items in the cargo hull of a pirate ship.
 * 
 * @author Justin Smith
 * @version Fall 2025
 */
public class Container {
	private static int idCounter = 1;
	private int size;
	private Collection<Stock> stockItems;
	private int containerID;
	private Collection<SpecialQualities> specialQualities;
	private String specialQualitiesString;
	private int remainingCapacity;
	private StockType stockType;

	/**
	 * Instantiates a new container from user input.
	 *
	 * @param size             the size
	 * @param specialQualities the special qualities of the container
	 * @param stockType        the stock type the container can hold
	 */
	public Container(int size, Collection<SpecialQualities> specialQualities, StockType stockType) {
		if (size <= 0) {
			throw new IllegalArgumentException("Size must be greater than zero.");
		}
		this.size = size;
		this.remainingCapacity = size;
		this.stockItems = new ArrayList<Stock>();
		this.containerID = idCounter++;
		this.specialQualities = specialQualities;
		this.stockType = stockType;
		this.getSpecialQualitiesAsString();
	}

	/**
	 * Instantiates a new container from saved data.
	 *
	 * @param size             the size of the container
	 * @param stock            the stock items in the container
	 * @param id               the container ID of the container
	 * @param specialQualities the special qualities of the container
	 * @param stockType        the stock type the container can hold
	 */
	public Container(int size, Collection<Stock> stock, int id, Collection<SpecialQualities> specialQualities, StockType stockType) {
		if (size <= 0) {
			throw new IllegalArgumentException("Size must be greater than zero.");
		}
		if (id <= 0) {
			throw new IllegalArgumentException("Container ID must be greater than zero.");
		}
		this.size = size;
		this.containerID = id;
		
		if (stock == null) {
		    this.stockItems = new ArrayList<>();
		} else {
		    this.stockItems = stock;
		}
		
		if (specialQualities == null) {
	        this.specialQualities = new ArrayList<>();
	    } else {
	        this.specialQualities = specialQualities;
	    }
		
		if (stockType == null) {
			throw new IllegalArgumentException("Stock type cannot be null.");
		} else {
			this.stockType = stockType;
		}
	
		this.getSpecialQualitiesAsString();
		this.calculateRemainingCapacity();
	}

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * Gets the stock items.
	 *
	 * @return the stock items
	 */
	public Collection<Stock> getStockItems() {
		return this.stockItems;
	}

	/**
	 * Gets the container ID.
	 *
	 * @return the container ID
	 */
	public int getContainerID() {
		return this.containerID;
	}

	/**
	 * Gets the special qualities.
	 *
	 * @return the special qualities
	 */
	public Collection<SpecialQualities> getSpecialQualities() {
		return this.specialQualities;
	}
	
	/**
	 * Gets the stock type.
	 *
	 * @return the stock type
	 */
	public StockType getStockType() {
		return this.stockType;
	}
	
	/**
	 * Gets the special qualities as a string.
	 *
	 * @return the special qualities string
	 */
	public String getSpecialQualitiesString() {
		return this.specialQualitiesString;
	}
	
	/**
	 * Gets the remaining capacity.
	 *
	 * @return the remaining capacity
	 */
	public int getRemainingCapacity() {
		return this.remainingCapacity;
	}

	/**
	 * Adds the special quality.
	 * 
	 * @precondition quality != null && !specialQualities.contains(quality)
	 * @postcondition specialQualities.contains(quality)
	 *
	 * @param quality the special quality to add
	 * @throws IllegalArgumentException if the special quality is null
	 * @throws IllegalArgumentException if the special quality already exists in the
	 *                                  container
	 */
	public void addSpecialQuality(SpecialQualities quality) {
		if (quality == null) {
			throw new IllegalArgumentException("Special quality cannot be null.");
		}
		if (this.specialQualities.contains(quality)) {
			throw new IllegalArgumentException("Special quality already exists in the container.");
		}
		this.specialQualities.add(quality);
	}

	/**
	 * Adds the stock item to the container if there is enough remaining capacity.
	 *
	 * @param stock      the stock item to be added
	 * @param crewmember the crewmember adding the stock item
	 * @return the transaction record of the addition
	 * @throws IllegalArgumentException if there is not enough remaining capacity in
	 *                                  the container
	 */
	public Transaction addStockItem(Stock stock, User crewmember) throws IllegalArgumentException {
		Transaction transaction = null;
		if (this.remainingCapacity >= stock.getTotalSize()) {
			this.stockItems.add(stock);
			this.calculateRemainingCapacity();
			transaction = new Transaction(new Date(), stock.getName(), stock.getQuantity(), crewmember, stock.getStockType(),
					new ArrayList<SpecialQualities>(stock.getSpecialQualities()));
		} else {
			throw new IllegalArgumentException("Not enough remaining capacity in the container.");
		}
		return transaction;
	}
	
	
	/**
	 * Removes the stock item from the container.
	 * 
	 * @param stock The stock to remove
	 * 
	 * @param crewMember The crew member responsible for the removal
	 */
	public Transaction removeStockItem(Stock stock, User crewMember) {
		if (!(stock instanceof Stock)) {
			throw new IllegalArgumentException("stock must be an instance of Stock");
		}
		if (!(crewMember instanceof User)) {
			throw new IllegalArgumentException("crewMember must be an instance of User");
		}
		if (!this.stockItems.contains(stock)) {
			throw new NoSuchElementException("stock must be currently stored in the container");
		}
		
		this.stockItems.remove(stock);
		this.calculateRemainingCapacity();
		var removedAmount = stock.getQuantity() * -1;
		var specialQualities = new ArrayList<SpecialQualities>(stock.getSpecialQualities());
		
		var removal = new Transaction(new Date(), stock.getName(), removedAmount, crewMember, stock.getStockType(), specialQualities);
		
		return removal;
	}

	/**
	 * Gets the current load of the container.
	 *
	 * @return the current load of the container
	 */
	public int calculateCurrentLoad() {
		int totalLoad = 0;
		for (Stock stock : this.stockItems) {
			totalLoad += stock.getTotalSize();
		}
		return totalLoad;
	}

	private void calculateRemainingCapacity() {
		this.remainingCapacity = this.size - this.calculateCurrentLoad();
	}

	private void getSpecialQualitiesAsString() {
		StringBuilder qualities = new StringBuilder();
		for (SpecialQualities quality : this.specialQualities) {
			qualities.append(quality.toString()).append(", ");
		}
		this.specialQualitiesString = qualities.toString().trim();
	}
	
	/**
	 * Updates the static ID counter so new containers do not conflict with previously loaded containers.
	 *
	 * @param usedIds the collection of existing container IDs
	 */
	public static void updateIdCounter(Collection<Integer> usedIds) {
	    int max = 0;
	    for (int id : usedIds) {
	        if (id > max) {
	            max = id;
	        }
	    }
	    idCounter = max + 1;
	}


	/**
	 * Returns a string representation of the container.
	 *
	 * @return a string representation of the container
	 */
	@Override
	public String toString() {
		return "Container ID: " + this.containerID + ", Remaining Capacity: " + this.remainingCapacity + ", Stock Type: " + this.stockType
				+ ", Special Qualities: [" + this.specialQualitiesString + "]";
	}
	
	/**
	 * Returns a boolean for whether the containers contains the given stock.
	 * 
	 * @param stock The stock to check for
	 * @return a boolean for if the container contains the given stock
	 */
	public boolean contains(Stock stock)
	{
		if (!(stock instanceof Stock)) {
			throw new IllegalArgumentException("stock must be an instance of Stock");
		}
		return this.stockItems.contains(stock);
	}

}
