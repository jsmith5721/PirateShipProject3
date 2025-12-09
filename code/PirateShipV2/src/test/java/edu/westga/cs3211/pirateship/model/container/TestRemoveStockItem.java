package edu.westga.cs3211.pirateship.model.container;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.Conditions;
import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.Stock;
import edu.westga.cs3211.pirateship.model.StockType;
import edu.westga.cs3211.pirateship.model.User;

class TestRemoveStockItem {
	User crewMember;
	Container testContainer;
	Stock stock;

	@BeforeEach
	void setUp() {
		var size = 5;
		var specialQuals = new ArrayList<SpecialQualities>();
		var type = StockType.OTHER;
		this.testContainer = new Container(size, specialQuals, type);
		
		this.crewMember = new User("Tester", "tester1", "password123", Roles.QUARTERMASTER);
		
		this.stock = new Stock("Matches", 1, 1, Conditions.NEW, StockType.OTHER);
	}

	@Test
	void testExceptionWhenNullStock() {
		assertThrows(IllegalArgumentException.class, () -> {this.testContainer.removeStockItem(null, this.crewMember);});
	}
	
	@Test
	void testExceptionWhenNullCrewmember() {
		assertThrows(IllegalArgumentException.class, () -> {this.testContainer.removeStockItem(this.stock, null);});
	}
	
	@Test
	void testExceptionWhenStockNotInContainer() {
		assertThrows(NoSuchElementException.class, () -> {this.testContainer.removeStockItem(this.stock, this.crewMember);});
	}
	
	@Test
	void testWhenLastStockRemoved() {
		this.testContainer.addStockItem(this.stock, this.crewMember);
		this.testContainer.removeStockItem(this.stock, this.crewMember);
		
		var expected = new ArrayList<Stock>();
		var actual = this.testContainer.getStockItems();
		
		assertIterableEquals(expected, actual);
	}
}
