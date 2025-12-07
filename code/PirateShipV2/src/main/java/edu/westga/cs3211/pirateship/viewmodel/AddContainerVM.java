package edu.westga.cs3211.pirateship.viewmodel;

import java.util.ArrayList;
import java.util.List;

import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.Ship;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
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

	/**
	 * Instantiates a new adds the container VM.
	 *
	 * @param ship the ship
	 */
	public AddContainerVM(Ship ship) {
		this.ship = ship;
		this.size = new SimpleIntegerProperty();

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
		Container container = new Container(this.size.get(), this.selectedSpecialQualities.getValue());

		try {
			this.ship.addContainer(container);
			//Debugging output
			System.out.println("Container added VM Level: " + container.toString());
			return container.toString();
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}

		return "";
	}

	/**
	 * Save data.
	 */
	public void saveData() {
		this.ship.saveData();
	}
}
