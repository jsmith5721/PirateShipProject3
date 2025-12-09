package edu.westga.cs3211.pirateship.model.serializer.userserializer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.model.serializers.ShipSerializer;
import edu.westga.cs3211.pirateship.model.serializers.UserSerializer;


public class TestLoadUsers {
	Ship ship;
	User user;
	User crewMember1;
	User crewMember2;
	
	@BeforeEach
	public void setUp() {
		this.ship = new Ship("Black Pearl", 10000);
		this.user = new User("Jack Sparrow", "password", "Captain", Roles.QUARTERMASTER);
		this.ship.setCurrentUser(this.user);
		
		crewMember1 = new User("Will Turner", "willt", "swordfish", Roles.CREWMATE);
		crewMember2 = new User("Elizabeth Swann", "lizs", "piratequeen", Roles.QUARTERMASTER);
	}
	
	@Test
	public void testSaveUsersNoUsers() {
		ShipSerializer.saveShip(ship, "TestUsers.txt", "TestCargo.txt", "TestTransactions.txt");
		Ship loadedShip = ShipSerializer.loadShip("TestUsers.txt", "TestCargo.txt", "TestTransactions.txt");
		
		assertEquals("Black Pearl", loadedShip.getName());
		assertEquals(10000, loadedShip.getCargoHull().getCapacity());
		assertEquals(0, loadedShip.getCrew().size());
		assertEquals(0, loadedShip.getContainers().size());
		assertEquals(0, loadedShip.getTransactions().size());
	}
	
	@Test
	public void testSaveShipWithCrewCargoAndTransactions() {
		ship.addCrewMember(crewMember1);
		ship.addCrewMember(crewMember2);
		
		UserSerializer.saveUsers(ship, "TestUsers.txt");
		ship.getCrew().clear();
		UserSerializer.loadUsers(ship, "TestUsers.txt");
		
		ArrayList<String> loadedCrewNames = new ArrayList<String>();
		for (User crewMember : ship.getCrew()) {
			loadedCrewNames.add(crewMember.getName());
		}
		
		assertEquals("Black Pearl", ship.getName());
		assertEquals(10000, ship.getCargoHull().getCapacity());
		assertTrue(loadedCrewNames.contains(crewMember1.getName()));
		assertTrue(loadedCrewNames.contains(crewMember2.getName()));
	}
	
	@Test
	public void testLoadUsersFileNotFound() {
		UserSerializer.loadUsers(ship, "NonExistentFile.txt");
		assertTrue(true);
	}
}
