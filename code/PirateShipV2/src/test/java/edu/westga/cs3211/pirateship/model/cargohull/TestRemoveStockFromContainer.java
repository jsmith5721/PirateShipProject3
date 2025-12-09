package edu.westga.cs3211.pirateship.model.cargohull;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.CargoHull;
import edu.westga.cs3211.pirateship.model.Conditions;
import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.Stock;
import edu.westga.cs3211.pirateship.model.StockType;
import edu.westga.cs3211.pirateship.model.User;

class TestRemoveStockFromContainer {
	CargoHull hull;
	Container container;
	Stock stock;
	User crewMember;

	@BeforeEach
	void setUp() {
		this.hull = new CargoHull(500);
		this.container = new Container(1, new ArrayList<SpecialQualities>(), StockType.FOOD);
		this.stock = new Stock("test stock", 1, 1, new ArrayList<SpecialQualities>(), Conditions.NEW, StockType.FOOD);
		this.crewMember = new User("jimmy", "jimmy1", "password123", Roles.COOK);
	}

	@Test
	void testExceptionWhenNullStock() {
		assertThrows(IllegalArgumentException.class, () -> {this.hull.removeStockFromContainer(null, 0, this.crewMember);});
	}

	@Test
	void testExceptionWhenNullCrewMember() {
		assertThrows(IllegalArgumentException.class, () -> {this.hull.removeStockFromContainer(this.stock, 0, null);});
	}
	
	@Test
	void testExceptionWhenInvalidContainerId() {
		assertThrows(IllegalArgumentException.class, () -> {this.hull.removeStockFromContainer(this.stock, 0, this.crewMember);});
	}
	
	@Test
	void testWhenSingleStockToRemoveMatches() {
		this.hull.addContainer(this.container);
		this.hull.addStockToContainer(this.stock, this.container.getContainerID(), this.crewMember);
		this.hull.removeStockFromContainer(this.stock, this.container.getContainerID(), this.crewMember);
		
		var expectedCount = 2;
		var actualCount = this.hull.getTransactionHistory().size();
		
		assertEquals(expectedCount, actualCount);
	}
}
