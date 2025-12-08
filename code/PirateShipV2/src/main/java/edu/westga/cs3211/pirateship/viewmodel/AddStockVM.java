package edu.westga.cs3211.pirateship.viewmodel;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import edu.westga.cs3211.pirateship.model.Conditions;
import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.Stock;
import edu.westga.cs3211.pirateship.model.StockType;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.model.serializers.ShipSerializer;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * ViewModel for adding stock items.
 * 
 * @author Justin Smith
 * @version Fall 2025
 */
public class AddStockVM {
	private Ship ship;
	private StringProperty name;
	private IntegerProperty size;
	private IntegerProperty quantity;
	private ObjectProperty<Conditions> condition;
	private ObjectProperty<LocalDate> expirationDate;
	private ListProperty<SpecialQualities> specialQualitiesList;
	private ListProperty<SpecialQualities> selectedSpecialQualities;
	private ObservableList<Container> masterContainerList;
	private ListProperty<Container> filteredContainerList;
	private ObjectProperty<Container> selectedContainer;
	private BooleanProperty showExpiration;
	private ObjectProperty<StockType> stockType;

	/**
	 * Instantiates a new AddStockVM.
	 *
	 * @param currentUser the currently logged in user
	 */
	public AddStockVM(User currentUser) {
		try {
			this.ship = ShipSerializer.loadShip();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		this.ship.setCurrentUser(currentUser);
		
		this.name = new SimpleStringProperty();
		this.size = new SimpleIntegerProperty();
		this.quantity = new SimpleIntegerProperty(1);
		this.condition = new SimpleObjectProperty<>();
		this.stockType = new SimpleObjectProperty<>();
		this.expirationDate = new SimpleObjectProperty<>();

		ArrayList<SpecialQualities> qualitiesList = new ArrayList<>();
		qualitiesList.add(SpecialQualities.PARISHABLE);
		qualitiesList.add(SpecialQualities.FRAGILE);
		qualitiesList.add(SpecialQualities.EXPLOSIVE);
		qualitiesList.add(SpecialQualities.LIQUID);
		qualitiesList.add(SpecialQualities.VALUABLE);

		this.specialQualitiesList = new SimpleListProperty<>(FXCollections.observableArrayList(qualitiesList));
		this.selectedSpecialQualities = new SimpleListProperty<>(FXCollections.observableArrayList());

		this.masterContainerList = FXCollections.observableArrayList(this.ship.getContainers());

		this.filteredContainerList = new SimpleListProperty<>(FXCollections.observableArrayList());
		this.filteredContainerList.addAll(this.masterContainerList);

		this.selectedContainer = new SimpleObjectProperty<>();

		this.showExpiration = new SimpleBooleanProperty(false);
		BooleanBinding binding = new BooleanBinding() {
			{
				super.bind(AddStockVM.this.selectedSpecialQualities);
			}

			@Override
			protected boolean computeValue() {
				return AddStockVM.this.selectedSpecialQualities.contains(SpecialQualities.PARISHABLE);
			}
		};
		this.showExpiration.bind(binding);
	}

	/**
	 * Gets the name property.
	 *
	 * @return the name property
	 */
	public StringProperty getNameProperty() {
		return this.name;
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
	 * Gets the quantity property.
	 *
	 * @return the quantity property
	 */
	public IntegerProperty getQuantityProperty() {
		return this.quantity;
	}

	/**
	 * Gets the condition property.
	 *
	 * @return the condition property
	 */
	public ObjectProperty<Conditions> getConditionProperty() {
		return this.condition;
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
	 * Gets the expiration date property.
	 *
	 * @return the expiration date property
	 */
	public ObjectProperty<LocalDate> getExpirationDateProperty() {
		return this.expirationDate;
	}

	/**
	 * Gets the special qualities list property.
	 *
	 * @return the special qualities list property
	 */
	public ListProperty<SpecialQualities> getSpecialQualitiesListProperty() {
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
	 * Gets the container list property.
	 *
	 * @return the container list property
	 */
	public ListProperty<Container> getContainerListProperty() {
		return this.filteredContainerList;
	}

	/**
	 * Gets the selected container property.
	 *
	 * @return the selected container property
	 */
	public ObjectProperty<Container> getSelectedContainerProperty() {
		return this.selectedContainer;
	}

	/**
	 * Show expiration property.
	 *
	 * @return the boolean property
	 */
	public BooleanProperty showExpirationProperty() {
		return this.showExpiration;
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
	 * Update selected qualities.
	 *
	 * @param selected the selected
	 */
	public void updateSelectedQualities(List<SpecialQualities> selected) {
		if (selected == null) {
			throw new NullPointerException("Selected qualities cannot be null.");
		}
		this.selectedSpecialQualities.set(FXCollections.observableArrayList(selected));
		this.applyContainerFilter();
	}
	
	/**
	 * Update selected stock type.
	 *
	 * @param selectedType the selected type
	 */
	public void updateSelectedStockType(StockType selectedType) {
		if (selectedType == null) {
			throw new NullPointerException("Selected stock type cannot be null.");
		}
		this.stockType.set(selectedType);
		this.applyContainerFilter();
	}

	/**
	 * Apply container filter.
	 */
	private void applyContainerFilter() {
		this.masterContainerList.setAll(this.ship.getContainers());
		this.filteredContainerList.clear();
		
		StockType selectedType = this.stockType.get();
		List<SpecialQualities> selected = this.selectedSpecialQualities.get();
		ArrayList<Container> result = new ArrayList<>();
		
		result = this.filterStockType(this.masterContainerList, selectedType);
		result = this.filterSpecialQualities(result, selected);
		this.filteredContainerList.addAll(result);
	}

	private ArrayList<Container> filterSpecialQualities(List<Container> containerList, List<SpecialQualities> selected) {
		ArrayList<Container> result = new ArrayList<>();
		if (selected == null || selected.isEmpty()) {
			result.addAll(containerList);
			return result;
		}
		for (Container container : containerList) {
			if (container.getSpecialQualities().containsAll(selected)) {
				result.add(container);
			}
		}
		return result;
	}

	private ArrayList<Container> filterStockType(List<Container> containerList, StockType selectedType) {
		ArrayList<Container> result = new ArrayList<>();
		if (selectedType == null) {
			result.addAll(containerList);
			return result;
		}
		for (Container container : containerList) {
			if (container.getStockType() == selectedType) {
				result.add(container);
			}
		}
		return result;
	}

	/**
	 * Adds the stock.
	 *
	 * @return the string
	 */
	public String addStock() {
		if (this.name.get() == null || this.name.get().isBlank()) {
			throw new IllegalArgumentException("Name cannot be blank.");
		}
		if (this.size.get() <= 0) {
			throw new IllegalArgumentException("Size must be greater than zero.");
		}
		if (this.quantity.get() <= 0) {
			throw new IllegalArgumentException("Quantity must be greater than zero.");
		}
		if (this.condition.get() == null) {
			throw new IllegalArgumentException("Condition must be selected.");
		}
		if (this.stockType.get() == null) {
			throw new IllegalArgumentException("Stock type must be selected.");
		}
		if (this.selectedContainer.get() == null) {
			throw new IllegalArgumentException("A container must be selected.");
		}

		ArrayList<SpecialQualities> qualities = new ArrayList<>(this.selectedSpecialQualities);
		boolean perishable = qualities.contains(SpecialQualities.PARISHABLE);

		Stock stock;

		if (perishable) {
			if (this.expirationDate.get() == null) {
				throw new IllegalArgumentException("Expiration date must be selected.");
			}
			stock = new Stock(this.name.get(), this.quantity.get(), this.size.get(),
					qualities, this.condition.get(), this.stockType.get(), this.expirationDate.get());
		} else if (!qualities.isEmpty()) {
			stock = new Stock(this.name.get(), this.quantity.get(), this.size.get(),
					qualities, this.condition.get(), this.stockType.get());
		} else {
			stock = new Stock(this.name.get(), this.quantity.get(), this.size.get(),
					this.condition.get(), this.stockType.get());
		}
		
		this.ship.addStockToContainer(this.selectedContainer.get().getContainerID(), stock);
		
		int selectedContainerId = this.selectedContainer.get().getContainerID();

		this.refreshContainerLists();

		return "Stock \"" + this.name.get() + "\" added to Container "
				+ selectedContainerId + ".";
	}

	private void refreshContainerLists() {
		this.masterContainerList.setAll(this.ship.getContainers());

		this.applyContainerFilter();

		List<Container> current = new ArrayList<>(this.filteredContainerList);
		this.filteredContainerList.set(FXCollections.observableArrayList(current));
	}
	
	/**
	 * Save data.
	 */
	public void saveData() {
		try {
			ShipSerializer.saveShip(this.ship, ShipSerializer.USERS_TXT_FILE, ShipSerializer.CARGO_TXT_FILE, ShipSerializer.TRANSACTION_TXT_FILE);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
