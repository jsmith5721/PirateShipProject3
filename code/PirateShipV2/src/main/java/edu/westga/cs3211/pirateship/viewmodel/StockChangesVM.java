package edu.westga.cs3211.pirateship.viewmodel;

import java.time.LocalDate;
import java.util.ArrayList;

import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.Transaction;
import edu.westga.cs3211.pirateship.model.User;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

/**
 * ViewModel for stock changes.
 * 
 * @author Justin Smith
 * @version Fall 2025
 */
public class StockChangesVM {
	private Ship ship;
	private ListProperty<Transaction> masterTransactionList;
	private ListProperty<Transaction> filteredTransactionList;
	private ListProperty<SpecialQualities> specialQualitiesList;
	private ListProperty<SpecialQualities> selectedSpecialQualities;
	private ListProperty<String> crewmemberList;
	private ObjectProperty<String> selectedCrewmember;
	private ObjectProperty<LocalDate> startDate;
	private ObjectProperty<LocalDate> endDate;

	/**
	 * Instantiates a new StockChangesVM.
	 *
	 * @param ship the ship
	 */
	public StockChangesVM(Ship ship) {
		this.ship = ship;

		this.masterTransactionList = new SimpleListProperty<>(
				FXCollections.observableArrayList(this.ship.getTransactions()));

		this.filteredTransactionList = new SimpleListProperty<>(
				FXCollections.observableArrayList(this.masterTransactionList));

		this.specialQualitiesList = new SimpleListProperty<>(
				FXCollections.observableArrayList(SpecialQualities.values()));
		this.selectedSpecialQualities = new SimpleListProperty<>(FXCollections.observableArrayList());

		ArrayList<String> names = new ArrayList<>();
		for (User user : this.ship.getCrew()) {
			names.add(user.getName());
		}
		this.crewmemberList = new SimpleListProperty<>(FXCollections.observableArrayList(names));
		this.selectedCrewmember = new SimpleObjectProperty<>();

		this.startDate = new SimpleObjectProperty<>(null);
		this.endDate = new SimpleObjectProperty<>(null);
	}

	/**
	 * Filtered transactions property.
	 *
	 * @return the list property
	 */
	public ListProperty<Transaction> filteredTransactionsProperty() {
		return this.filteredTransactionList;
	}

	/**
	 * Special qualities list property.
	 *
	 * @return the list property
	 */
	public ListProperty<SpecialQualities> specialQualitiesListProperty() {
		return this.specialQualitiesList;
	}

	/**
	 * Selected special qualities property.
	 *
	 * @return the list property
	 */
	public ListProperty<SpecialQualities> selectedSpecialQualitiesProperty() {
		return this.selectedSpecialQualities;
	}

	/**
	 * Crewmember list property.
	 *
	 * @return the list property
	 */
	public ListProperty<String> crewmemberListProperty() {
		return this.crewmemberList;
	}

	/**
	 * Selected crewmember property.
	 *
	 * @return the object property
	 */
	public ObjectProperty<String> selectedCrewmemberProperty() {
		return this.selectedCrewmember;
	}

	/**
	 * Start date property.
	 *
	 * @return the object property
	 */
	public ObjectProperty<LocalDate> startDateProperty() {
		return this.startDate;
	}

	/**
	 * End date property.
	 *
	 * @return the object property
	 */
	public ObjectProperty<LocalDate> endDateProperty() {
		return this.endDate;
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
	 * Re-applies all filters.
	 */
	public void applyFilter() {

		ArrayList<Transaction> result = new ArrayList<>();

		for (Transaction transaction : this.masterTransactionList) {
			result.add(transaction);
		}

		result = this.filterForDate(result);

		if (!this.selectedSpecialQualities.isEmpty()) {
			result = this.filterForSpecialQualities(result);
		}

		if (this.selectedCrewmember.get() != null && !this.selectedCrewmember.get().isBlank()) {
			result = this.filterForCrewMember(result);
		}

		this.filteredTransactionList.set(FXCollections.observableArrayList(result));
	}

	private ArrayList<Transaction> filterForCrewMember(ArrayList<Transaction> transactions) {
		ArrayList<Transaction> crewFiltered = new ArrayList<>();

		for (Transaction transaction : transactions) {
			if (transaction.getCrewmember().getName().equals(this.selectedCrewmember.get())) {
				crewFiltered.add(transaction);
			}
		}
		return crewFiltered;
	}

	private ArrayList<Transaction> filterForSpecialQualities(ArrayList<Transaction> transactions) {
		ArrayList<Transaction> qualityFiltered = new ArrayList<>();

		for (Transaction transaction : transactions) {
			boolean hasAll = true;

			for (SpecialQualities quality : this.selectedSpecialQualities) {
				if (!transaction.getSpecialQualities().contains(quality)) {
					hasAll = false;
					break;
				}
			}

			if (hasAll) {
				qualityFiltered.add(transaction);
			}
		}
		
		return qualityFiltered;
	}

	private ArrayList<Transaction> filterForDate(ArrayList<Transaction> transactions) {
		ArrayList<Transaction> dateFiltered = new ArrayList<>();
		for (Transaction transaction : transactions) {

			LocalDate transactionDate = transaction.getDate().toInstant().atZone(java.time.ZoneId.systemDefault())
					.toLocalDate();

			boolean passes = true;

			if (this.startDate.get() != null) {
				if (transactionDate.isBefore(this.startDate.get())) {
					passes = false;
				}
			}

			if (this.endDate.get() != null) {
				if (transactionDate.isAfter(this.endDate.get())) {
					passes = false;
				}
			}

			if (passes) {
				dateFiltered.add(transaction);
			}
		}

		return dateFiltered;
	}
}
