package edu.westga.cs3211.pirateship.viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.CargoHull;
import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.Stock;
import edu.westga.cs3211.pirateship.model.StockType;

/**
 * ViewModel for the View Inventory page.
 * Responsible for holding and populating the list of stocks
 * shown in the inventory table.
 */
public class ViewInventoryVM {

    private final ObservableList<Stock> stocks;
    private Ship ship;

    public ViewInventoryVM(Ship ship) {
        this.stocks = FXCollections.observableArrayList();
        this.ship = ship;
    }

    public ObservableList<Stock> getStocks() {
        return this.stocks;
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
}




