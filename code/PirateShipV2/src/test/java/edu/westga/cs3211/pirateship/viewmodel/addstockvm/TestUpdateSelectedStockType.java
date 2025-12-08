package edu.westga.cs3211.pirateship.viewmodel.addstockvm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.StockType;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.viewmodel.AddStockVM;

public class TestUpdateSelectedStockType {
	User currentUser;
	AddStockVM vm;
	
	@BeforeEach
	void setUp() {
		currentUser = new User("A", "a", "p", Roles.QUARTERMASTER);
		vm = new AddStockVM(this.currentUser);
		vm.getShip().getContainers().clear();
		
		ArrayList<SpecialQualities> parishable = new ArrayList<>();
		parishable.add(SpecialQualities.PARISHABLE);
		
		ArrayList<SpecialQualities> fragileAndExplosive = new ArrayList<>();
		fragileAndExplosive.add(SpecialQualities.FRAGILE);
		fragileAndExplosive.add(SpecialQualities.EXPLOSIVE);
		
		Container food = new Container(100, parishable, StockType.FOOD);
		Container ammo = new Container(200, fragileAndExplosive, StockType.AMMUNITION);
		
		vm.getShip().addContainer(food);
		vm.getShip().addContainer(ammo);
		vm.getMasterContainerList().add(food);
		vm.getMasterContainerList().add(ammo);
	}
	
	@Test
	void testSelectedNull() {
		assertThrows(NullPointerException.class, () -> 
			vm.updateSelectedStockType(null)
		);
	}
	
	@Test
	void testSelectedTypeNoContainersMatch() {
		vm.updateSelectedStockType(StockType.OTHER);
		assertEquals(0, vm.getFilteredContainerList().size());
	}
	
	@Test
	void testSelectedTypeContainerMatchs() {
		vm.updateSelectedStockType(StockType.FOOD);
		assertEquals(1, vm.getFilteredContainerList().size());
	}
}
