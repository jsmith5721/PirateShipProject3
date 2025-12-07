package edu.westga.cs3211.pirateship.viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.CargoHull;
import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.Stock;

/**
 * ViewModel for the View Inventory page.
 * Responsible for holding and populating the list of stocks
 * shown in the inventory table.
 */
public class ViewInventoryVM {

    private final ObservableList<Stock> stocks;

    public ViewInventoryVM() {
        this.stocks = FXCollections.observableArrayList();
    }

    public ObservableList<Stock> getStocks() {
        return this.stocks;
    }

    /**
     * Populates the observable list with ALL stocks
     * from every container in the ship's cargo hull.
     *
     * No filtering — filtering is handled by teammates.
     */
    public void loadEntireInventory(Ship ship) {
        this.stocks.clear();

        if (ship == null) {
            return;
        }

        CargoHull cargoHull = ship.getCargoHull();   // adjust name if needed
        if (cargoHull == null || cargoHull.getContainers() == null) {
            return;
        }

        for (Container container : cargoHull.getContainers()) {
            if (container.getStockItems() == null) {
                continue;
            }
            this.stocks.addAll(container.getStockItems());
        }
    }
}




