package edu.westga.cs3211.pirateship.model.container;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.Conditions;
import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.Stock;
import edu.westga.cs3211.pirateship.model.StockType;
import edu.westga.cs3211.pirateship.model.User;

class TestContains {
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
		assertThrows(IllegalArgumentException.class, () -> {this.testContainer.contains(null);});
	}
	
	@Test
	void testWhenNoContainedStock() {
		var outcome = this.testContainer.contains(this.stock);
		
		assertFalse(outcome);
	}
	
	@Test
	void testWhenSingleContainedMatches() {
		this.testContainer.addStockItem(this.stock, this.crewMember);
		
		var outcome = this.testContainer.contains(this.stock);
		
		assertTrue(outcome);
	}
	
	@Test
	void testWhenMultipleContainedOneMatch() {
		var fillerStock = new Stock("filler", 1, 1, Conditions.NEW, StockType.OTHER);
		this.testContainer.addStockItem(fillerStock, this.crewMember);
		this.testContainer.addStockItem(this.stock, this.crewMember);
		
		var outcome = this.testContainer.contains(this.stock);
		
		assertTrue(outcome);
	}
	
	@Test
	void testWhenMultipleContainedNoMatch() {
		var fillerStock1 = new Stock("filler", 1, 1, Conditions.NEW, StockType.OTHER);
		var fillerStock2 = new Stock("filler2", 1, 1, Conditions.NEW, StockType.OTHER);
		this.testContainer.addStockItem(fillerStock1, this.crewMember);
		this.testContainer.addStockItem(fillerStock2, this.crewMember);
		
		var outcome = this.testContainer.contains(this.stock);
		
		assertFalse(outcome);
	}

}
