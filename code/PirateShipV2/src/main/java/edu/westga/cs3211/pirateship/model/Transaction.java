package edu.westga.cs3211.pirateship.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Models a transaction in the pirate ship system.
 * 
 * @author Justin Smith
 * @version Fall 2025
 */
public class Transaction {
	private Date date;
	private String stockName;
	private int quantity;
	private User crewmember;
	private StockType stockType;
	private Collection<SpecialQualities> specialQualities;
	private String specialQualitiesString;

	/**
	 * Instantiates a new transaction.
	 *
	 * @param date             the date of the transaction
	 * @param stockName        the name of the stock that was transacted.
	 * @param quantity         the quantity of stock that was transacted.
	 * @param crewmember       the crewmember who performed the transaction.
	 * @param stockType        the type of the stock that was transacted.
	 * @param specialQualities the special qualities of the stock that was
	 *                         transacted.
	 */
	public Transaction(Date date, String stockName, int quantity, User crewmember, StockType stockType,
			ArrayList<SpecialQualities> specialQualities) {
		this.date = date;
		this.stockName = stockName;
		this.quantity = quantity;
		this.crewmember = crewmember;
		this.stockType = stockType;
		this.specialQualities = specialQualities;
		this.specialQualitiesString = "";
		for (SpecialQualities quality : specialQualities) {
			this.specialQualitiesString += quality.toString() + ", ";
		}
	}

	/**
	 * Gets the date of the transaction.
	 *
	 * @return the date of the transaction.
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * Gets the name of the stock that was transacted.
	 *
	 * @return the name of the stock that was transacted.
	 */
	public String getStockName() {
		return this.stockName;
	}

	/**
	 * Gets the quantity of stock that was transacted.
	 *
	 * @return the quantity of stock that was transacted.
	 */
	public int getQuantity() {
		return this.quantity;
	}

	/**
	 * Gets the crewmember who performed the transaction.
	 *
	 * @return the crewmember who performed the transaction.
	 */
	public User getCrewmember() {
		return this.crewmember;
	}
	
	/**
	 * Gets the type of the stock that was transacted.
	 *
	 * @return the type of the stock that was transacted.
	 */
	public StockType getStockType() {
		return this.stockType;
	}

	/**
	 * Gets the special qualities of the stock that was transacted.
	 *
	 * @return the special qualities of the stock that was transacted.
	 */
	public Collection<SpecialQualities> getSpecialQualities() {
		return this.specialQualities;
	}

	/**
	 * Gets the special qualities of the stock that was transacted.
	 *
	 * @return the special qualities of the stock that was transacted.
	 */
	public String getSpecialQualitiesString() {
		return this.specialQualitiesString;
	}

	@Override
	public String toString() {
		return "Date: " + this.date + ", Stock Name: " + this.stockName + ", Quantity: " + this.quantity
				+ ", Crewmember: " + this.crewmember.getUsername() + ", Stock Type: " + this.stockType.name() + ", Special Qualities: "
				+ this.specialQualitiesString;
	}
}
