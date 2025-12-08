package edu.westga.cs3211.pirateship.viewmodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.StockType;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.model.serializers.ShipSerializer;
import edu.westga.cs3211.pirateship.model.Ship;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

/**
 * The Class AddContainerVM.
 * 
 * @author Justin Smith
 * @version Fall 2025
 */
public class AddContainerVM {
	private Ship ship;
	private IntegerProperty size;
	private ListProperty<SpecialQualities> specialQualitiesList;
	private ListProperty<SpecialQualities> selectedSpecialQualities;
	private ObjectProperty<StockType> stockType;

	/**
	 * Instantiates a new adds the container VM.
	 *
	 * @param currentUser the currently logged in user
	 */
	public AddContainerVM(User currentUser) {
		try {
			this.ship = ShipSerializer.loadShip();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		this.ship.setCurrentUser(currentUser);
		
		this.size = new SimpleIntegerProperty();
		this.stockType = new SimpleObjectProperty<>();

		ArrayList<SpecialQualities> qualitiesList = new ArrayList<SpecialQualities>();
		qualitiesList.add(SpecialQualities.PARISHABLE);
		qualitiesList.add(SpecialQualities.FRAGILE);
		qualitiesList.add(SpecialQualities.EXPLOSIVE);
		qualitiesList.add(SpecialQualities.LIQUID);
		qualitiesList.add(SpecialQualities.VALUABLE);

		this.specialQualitiesList = new SimpleListProperty<>(FXCollections.observableArrayList(qualitiesList));
		this.selectedSpecialQualities = new SimpleListProperty<>(FXCollections.observableArrayList());
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
	 * Gets the size property.
	 *
	 * @return the size property
	 */
	public IntegerProperty getSizeProperty() {
		return this.size;
	}
	
	/**
	 * Gets the stock type property.
	 *
	 * @return the stock type property
	 */
	public ObjectProperty<StockType> getStockTypeProperty() {
		return this.stockType;
	}

	/**
	 * Gets the special quality property.
	 *
	 * @return the special quality property
	 */
	public ListProperty<SpecialQualities> getSpecialQualityProperty() {
		return this.specialQualitiesList;
	}

	/**
	 * Gets the selected special qualities property.
	 *
	 * @return the selected special qualities property
	 */
	public ListProperty<SpecialQualities> getSelectedSpecialQualitiesProperty() {
		return this.selectedSpecialQualities;
	}

	/**
	 * Sets the selected special qualities from the UI.
	 *
	 * @param selected the list from the ListView
	 */
	public void updateSelectedQualities(List<SpecialQualities> selected) {
		this.selectedSpecialQualities.set(FXCollections.observableArrayList(selected));
	}

	/**
	 * Adds the container.
	 * 
	 * @return the container's string representation or empty if failed
	 */
	public String addContainer() {
		String result = "";
		Container container = new Container(this.size.get(), this.selectedSpecialQualities.getValue(), this.stockType.get());

		try {
			this.ship.addContainer(container);
			result = container.toString();
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}

		return result;
	}

	/**
	 * Save the ship data.
	 */
	public void saveData() {
		try {
			ShipSerializer.saveShip(this.ship, ShipSerializer.USERS_TXT_FILE, ShipSerializer.CARGO_TXT_FILE, ShipSerializer.TRANSACTION_TXT_FILE);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
