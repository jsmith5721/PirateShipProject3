package edu.westga.cs3211.pirateship.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import edu.westga.cs3211.pirateship.model.Ship;

import java.io.IOException;

import edu.westga.cs3211.pirateship.model.CargoHull;
import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.Stock;
import edu.westga.cs3211.pirateship.model.StockType;
import edu.westga.cs3211.pirateship.model.serializers.ShipSerializer;

/**
 * ViewModel for the View Inventory page.
 * Responsible for holding and populating the list of stocks
 * shown in the inventory table.
 */
public class ViewInventoryVM {

    private final ObservableList<Stock> stocks;
	private final StringProperty welcomeMessageProperty;
    private Ship ship;
    
    

    public ViewInventoryVM(Ship ship) {
        if (ship == null) {
            throw new IllegalArgumentException("Ship cannot be null");
        }

        this.ship = ship;
        this.stocks = FXCollections.observableArrayList();

        if (ship.getCurrentUser() != null) {
            this.welcomeMessageProperty =
                new SimpleStringProperty("Welcome, " + ship.getCurrentUser().getName());
        } else {
            this.welcomeMessageProperty =
                new SimpleStringProperty("Welcome");
        }
    }


    public ObservableList<Stock> getStocks() {
        return this.stocks;
    }
    
    /**
	 * Welcome message property.
	 *
	 * @return the string property
	 */
	public StringProperty welcomeMessageProperty() {
		return this.welcomeMessageProperty;
	}

    /**
     * Populates the observable list with ALL stocks
     * from every container in the ship's cargo hull.
     *
     * The visible stock is determined by the role of the currently logged-in user
     */
    public void loadEntireInventory(Ship ship) {
        this.stocks.clear();

        if (ship == null) {
            return;
        }
        
        Roles currentUserRole = ship.getCurrentUser().getRole();
        CargoHull cargoHull = ship.getCargoHull();
        
        if (cargoHull == null || cargoHull.getContainers() == null) {
            return;
        }

        for (Container container : cargoHull.getContainers()) {
            if (container.getStockItems() == null) {
                continue;
            }
            StockType containerType = container.getStockType();
            if (this.roleCanSeeStockType(currentUserRole, containerType)) {
            	this.stocks.addAll(container.getStockItems());
            }
        }
    }
    
    private boolean roleCanSeeStockType(Roles role, StockType stockType) {
    	if (!(role instanceof Roles)) {
    		throw new IllegalArgumentException("role must be from the Roles Enum");
    	}
    	if (!(stockType instanceof StockType)) {
    		throw new IllegalArgumentException("stockType must be from the StockType Enum");
    	}
    	
    	if (role == Roles.QUARTERMASTER)
    	{
    		return true;
    	}
    	if (role == Roles.COOK && stockType == StockType.FOOD) {
    		return true;
    	}
    	else if (role == Roles.OFFICER && stockType == StockType.AMMUNITION) {
    		return true;
    	}
    	return false;
    }
    
    public Ship getShip() {
		return this.ship;
	}
    
    /**
	 * Save data.
	 */
	public void saveData() {
		try {
			ShipSerializer.saveShip(this.ship, ShipSerializer.USERS_TXT_FILE, ShipSerializer.CARGO_TXT_FILE,
					ShipSerializer.TRANSACTION_TXT_FILE);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}




