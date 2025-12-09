package edu.westga.cs3211.pirateship.viewmodel.viewinventoryvm;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.Conditions;
import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.Stock;
import edu.westga.cs3211.pirateship.model.StockType;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.viewmodel.ViewInventoryVM;

class TestRemoveStock {
	Ship ship;
	User user;
	Container container;

	@BeforeEach
	void setUp() {
		this.ship = new Ship("Flying Dutchman", 500);
        this.user = new User("Emi", "emi", "pw", Roles.COOK);
        this.container = new Container(20, new ArrayList<SpecialQualities>(), StockType.FOOD);
        this.ship.setCurrentUser(this.user);
	}

	@Test
	void testExceptionWhenInvalidStocksToRemove() {
		ViewInventoryVM vm = new ViewInventoryVM(ship);
		
		assertThrows(IllegalArgumentException.class, () -> {vm.removeStock(null);});
	}
	
	@Test
	void testExceptionWhenNoExistingStocksAndNoneToRemove() {
		ViewInventoryVM vm = new ViewInventoryVM(ship);
		vm.removeStock(new ArrayList<Stock>());
		
		var expected = new ArrayList<Stock>();
		var actual = vm.getStocks();
		
		assertIterableEquals(expected, actual);
	}

	@Test
	void testWhenNoExistingStocksAndOneRemoved() {
		var stocks = new ArrayList<Stock>();
		var stockItem = new Stock("Apple", 1, 1, Conditions.NEW, StockType.FOOD);
		stocks.add(stockItem);
		ViewInventoryVM vm = new ViewInventoryVM(ship);
		vm.removeStock(stocks);
		
		var expected = new ArrayList<Stock>();
		var actual = vm.getStocks();
		
		assertIterableEquals(expected, actual);
	}
	
	@Test
	void testWhenRemovingStoredStocks() {
		var stocks = new ArrayList<Stock>();
		var stockItem = new Stock("Apple", 1, 1, Conditions.NEW, StockType.FOOD);
		stocks.add(stockItem);
		this.container.addStockItem(stockItem, this.user);
		this.ship.addContainer(this.container);
		ViewInventoryVM vm = new ViewInventoryVM(ship);
		vm.removeStock(stocks);
		
		var expected = new ArrayList<Stock>();
		var actual = vm.getStocks();
		
		assertIterableEquals(expected, actual);
	}
}
