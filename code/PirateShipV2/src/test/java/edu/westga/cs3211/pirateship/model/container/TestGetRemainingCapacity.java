package edu.westga.cs3211.pirateship.model.container;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.Conditions;
import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.Stock;
import edu.westga.cs3211.pirateship.model.StockType;
import edu.westga.cs3211.pirateship.model.User;

public class TestGetRemainingCapacity {
	private Container container;
	private Collection<SpecialQualities> qualities;
	private User currentUser;
	
	@BeforeEach
	public void setUp() throws Exception {
		this.qualities = new ArrayList<>();
		this.container = new Container(500, qualities, StockType.OTHER);
		this.currentUser = new User("Jack Sparrow", "jsparrow", "blackpearl", Roles.QUARTERMASTER);
	}
	
	@Test
	public void testGetRemainingCapacity() {
		int expected = 500;
		int actual = this.container.getRemainingCapacity();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetRemainingCapacityAfterAddingStock() {
		int expected = 300;
		Stock stockItem = new Stock("Gold Coins", 200, 1, Conditions.NEW, StockType.OTHER);
		this.container.addStockItem(stockItem, this.currentUser);
		int actual = this.container.getRemainingCapacity();
		assertEquals(expected, actual);
	}
}
