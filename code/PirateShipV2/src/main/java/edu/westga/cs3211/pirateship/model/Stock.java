package edu.westga.cs3211.pirateship.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Models a stock item.
 * @author Justin Smith
 * @version Fall 2025
 */
public class Stock {
	private String name;
	private int quantity;
	private int individualSize;
	private int totalSize;
	private Collection<SpecialQualities> specialQualities;
	private Conditions condition;
	private LocalDate expirationDate;
	
	/**
	 * Instantiates a new stock.
	 *
	 * @param name the name
	 * @param quantity the quantity
	 * @param size the size of each individual item
	 * @param condition the condition
	 */
	public Stock(String name, int quantity, int size, Conditions condition) {
		
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("Name cannot be null");
		}
		if (condition == null) {
			throw new IllegalArgumentException("Condition cannot be null");
		}
		if (quantity <= 0) {
			throw new IllegalArgumentException("Quantity must be greater than zero");
		}
		if (size <= 0) {
			throw new IllegalArgumentException("Size must be greater than zero");
		}
		this.name = name;
		this.quantity = quantity;
		this.individualSize = size;
		this.totalSize = size * quantity;
		this.specialQualities = new ArrayList<SpecialQualities>();
		this.condition = condition;
		this.expirationDate = null;
	}
	
	/**
	 * Instantiates a new stock.
	 *
	 * @param name the name
	 * @param quantity the quantity
	 * @param size the size of each individual item
	 * @param specialQualities the special qualities
	 * @param condition the condition
	 */
	public Stock(String name, int quantity, int size, Collection<SpecialQualities> specialQualities, Conditions condition) {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("Name cannot be null");
		}
		if (specialQualities == null) {
			throw new IllegalArgumentException("Special qualities cannot be null");
		}
		if (condition == null) {
			throw new IllegalArgumentException("Condition cannot be null");
		}
		if (quantity <= 0) {
			throw new IllegalArgumentException("Quantity must be greater than zero");
		}
		if (size <= 0) {
			throw new IllegalArgumentException("Size must be greater than zero");
		}
		this.name = name;
		this.quantity = quantity;
		this.individualSize = size;
		this.totalSize = this.individualSize * quantity;
		this.specialQualities = specialQualities;
		this.condition = condition;
		this.expirationDate = null;
	}
	
	/**
	 * Instantiates a new stock.
	 *
	 * @param name the name
	 * @param quantity the quantity
	 * @param size the size of each individual item
	 * @param specialQualities the special qualities
	 * @param condition the condition
	 * @param expirationDate the expiration date
	 */
	public Stock(String name, int quantity, int size, Collection<SpecialQualities> specialQualities, Conditions condition, LocalDate expirationDate) {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("Name cannot be null");
		}
		if (specialQualities == null) {
			throw new IllegalArgumentException("Special qualities cannot be null");
		}
		if (condition == null) {
			throw new IllegalArgumentException("Condition cannot be null");
		}
		if (expirationDate == null) {
			throw new IllegalArgumentException("Expiration date cannot be null");
		}
		if (expirationDate.isBefore(LocalDate.now())) {
			throw new IllegalArgumentException("Expiration date cannot be in the past");
		}
		if (quantity <= 0) {
			throw new IllegalArgumentException("Quantity must be greater than zero");
		}
		if (size <= 0) {
			throw new IllegalArgumentException("Size must be greater than zero");
		}
		this.name = name;
		this.quantity = quantity;
		this.individualSize = size;
		this.totalSize = size * quantity;
		this.specialQualities = specialQualities;
		this.condition = condition;
		this.expirationDate = expirationDate;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public int getQuantity() {
		return this.quantity;
	}
	
	/**
	 * Gets the total size.
	 *
	 * @return the total size
	 */
	public int getTotalSize() {
		return this.totalSize;
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
	 * Gets the condition.
	 *
	 * @return the condition
	 */
	public Conditions getCondition() {
		return this.condition;
	}
	
	/**
	 * Gets the expiration date.
	 *
	 * @return the expiration date
	 */
	public LocalDate getExpirationDate() {
		return this.expirationDate;
	}
}
