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

class TestGetContainerIdStoringStock {
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
	void testExceptionWhenNonStockArgument() {
		assertThrows(IllegalArgumentException.class, () -> {this.hull.getContainerIdStoringStock(null);});
	}
	
	@Test
	void testWhenNoContainers() {
		
		var expected = -1;
		var actual = this.hull.getContainerIdStoringStock(this.stock);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void testWhenStockStoredNowhere() {
		this.hull.addContainer(this.container);
		
		var expected = -1;
		var actual = this.hull.getContainerIdStoringStock(this.stock);
		
		assertEquals(expected, actual);
	}

	
	@Test
	void testWhenStockStoredInContainer() {
		this.hull.addContainer(this.container);
		this.container.addStockItem(stock, this.crewMember);
		
		var expected = this.container.getContainerID();
		var actual = this.hull.getContainerIdStoringStock(this.stock);
		
		assertEquals(expected, actual);
	}
}
