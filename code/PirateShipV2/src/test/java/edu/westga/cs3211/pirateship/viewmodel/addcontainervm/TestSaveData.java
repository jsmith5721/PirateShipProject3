package edu.westga.cs3211.pirateship.viewmodel.addcontainervm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.viewmodel.AddContainerVM;

public class TestSaveData {
	private class ShipDouble extends Ship {
		boolean saveShipDataCalled = false;
		public ShipDouble(String name, int capacity) {
			super(name, capacity);
		}
		
		public boolean getSaveShipDataCalled() {
			return this.saveShipDataCalled;
		}
		
		@Override
		public void saveShipData(String usersFile, String cargoFile, String transactionFile) {
			this.saveShipDataCalled = true;
		}
	}
	
	
	@Test
	public void testSaveData() {
		ShipDouble ship = new ShipDouble("Black Pearl", 1000);
		User user = new User("Jack Sparrow", "password", "Captain", Roles.QUARTERMASTER);
		AddContainerVM vm = new AddContainerVM(user, ship);
		vm.saveData();
		assertTrue(ship.getSaveShipDataCalled());
	}
}
