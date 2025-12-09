package edu.westga.cs3211.pirateship.model.ship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

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
import edu.westga.cs3211.pirateship.model.serializers.ShipSerializer;
import edu.westga.cs3211.pirateship.model.Transaction;
import edu.westga.cs3211.pirateship.viewmodel.AddContainerVM;

public class TestSaveShipData {
	Ship ship;
	User user;
	AddContainerVM vm;
	Container container1;
	Container container2;
	Container container3;
	User crewMember1;
	User crewMember2;
	Transaction transaction1;
	Transaction transaction2;
	Transaction transaction3;
	Transaction transaction4;
	
	private Date toDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
	
	@BeforeEach
	public void setUp() {
		this.ship = new Ship("Black Pearl", 10000);
		this.user = new User("Jack Sparrow", "password", "Captain", Roles.QUARTERMASTER);
		this.ship.setCurrentUser(this.user);
		
		List<SpecialQualities> qualitiesParish = new ArrayList<SpecialQualities>();
		qualitiesParish.add(SpecialQualities.PARISHABLE);
		
		List<SpecialQualities> qualitiesFragAndExplo = new ArrayList<SpecialQualities>();
		qualitiesFragAndExplo.add(SpecialQualities.FRAGILE);
		qualitiesFragAndExplo.add(SpecialQualities.EXPLOSIVE);
		
		List<SpecialQualities> qualitiesValuable = new ArrayList<SpecialQualities>();
		qualitiesValuable.add(SpecialQualities.VALUABLE);
		
		List<Stock> stockParish = new ArrayList<Stock>();
		Stock stock1 = new Stock("Fruit", 100, 1, qualitiesParish, Conditions.NEW, StockType.FOOD, LocalDate.of(2026, 5, 1));
		stockParish.add(stock1);
		
		List<Stock> stockFragAndExplo = new ArrayList<Stock>();
		Stock stock2 = new Stock("Ammo", 50, 2, qualitiesFragAndExplo, Conditions.GOOD, StockType.AMMUNITION);
		stockFragAndExplo.add(stock2);
		
		List<Stock> stockValuable = new ArrayList<Stock>();
		Stock stock3 = new Stock("Gold Coins", 200, 3, qualitiesValuable, Conditions.NEW, StockType.OTHER);
		Stock stock4 = new Stock("Gems", 50, 5, qualitiesValuable, Conditions.NEW, StockType.OTHER);
		stockValuable.add(stock3);
		stockValuable.add(stock4);
		
		container1 = new Container(500, stockParish, 1, qualitiesParish, StockType.FOOD);
		container2 = new Container(300, stockFragAndExplo, 2, qualitiesFragAndExplo, StockType.AMMUNITION);
		container3 = new Container(400, stockValuable, 3, qualitiesValuable, StockType.OTHER);
		
		crewMember1 = new User("Will Turner", "willt", "swordfish", Roles.CREWMATE);
		crewMember2 = new User("Elizabeth Swann", "lizs", "piratequeen", Roles.QUARTERMASTER);
		
		transaction1 = new Transaction(toDate(LocalDate.of(2024, 1, 10)), "Fruit", 100, crewMember1, StockType.FOOD, qualitiesParish);
		transaction2 = new Transaction(toDate(LocalDate.of(2024, 2, 15)), "Ammo", 50, crewMember2, StockType.AMMUNITION, qualitiesFragAndExplo);
		transaction3 = new Transaction(toDate(LocalDate.of(2024, 3, 10)), "Gold Coins", 200, crewMember1, StockType.OTHER, qualitiesValuable);
		transaction4 = new Transaction(toDate(LocalDate.of(2024, 4, 5)), "Gems", 50, crewMember2, StockType.OTHER, qualitiesValuable);
	}
	
	@Test
	public void testSaveShipDataNoCrewNoCargoNoTransaction() {
		ship.saveShipData("TestUsers.txt", "TestCargo.txt", "TestTransactions.txt");
		Ship loadedShip = ShipSerializer.loadShip("TestUsers.txt", "TestCargo.txt", "TestTransactions.txt");
		
		assertEquals("Black Pearl", loadedShip.getName());
		assertEquals(10000, loadedShip.getCargoHull().getCapacity());
		assertEquals(0, loadedShip.getCrew().size());
		assertEquals(0, loadedShip.getContainers().size());
		assertEquals(0, loadedShip.getTransactions().size());
	}
	
	@Test
	public void testSaveShipDataWithCrewCargoAndTransactions() {
		ship.addCrewMember(crewMember1);
		ship.addCrewMember(crewMember2);
		
		ship.getContainers().add(container1);
		ship.getContainers().add(container2);
		ship.getContainers().add(container3);
		
		ship.getTransactions().add(transaction1);
		ship.getTransactions().add(transaction2);
		ship.getTransactions().add(transaction3);
		ship.getTransactions().add(transaction4);
		
		ship.saveShipData("TestUsers.txt", "TestCargo.txt", "TestTransactions.txt");
		Ship loadedShip = ShipSerializer.loadShip("TestUsers.txt", "TestCargo.txt", "TestTransactions.txt");
		
		ArrayList<String> loadedCrewNames = new ArrayList<String>();
		for (User crewMember : loadedShip.getCrew()) {
			loadedCrewNames.add(crewMember.getName());
		}
		
		ArrayList<Integer> loadedContainerIds = new ArrayList<Integer>();
		for (Container container : loadedShip.getContainers()) {
			loadedContainerIds.add(container.getContainerID());
		}
		
		ArrayList<Date> loadedTransactionDates = new ArrayList<Date>();
		for (Transaction transaction : loadedShip.getTransactions()) {
			loadedTransactionDates.add(transaction.getDate());
		}
		
		assertEquals("Black Pearl", loadedShip.getName());
		assertEquals(10000, loadedShip.getCargoHull().getCapacity());
		assertTrue(loadedCrewNames.contains(crewMember1.getName()));
		assertTrue(loadedCrewNames.contains(crewMember2.getName()));
		assertTrue(loadedContainerIds.contains(container1.getContainerID()));
		assertTrue(loadedContainerIds.contains(container1.getContainerID()));
		assertTrue(loadedContainerIds.contains(container1.getContainerID()));
		assertTrue(loadedTransactionDates.contains(transaction1.getDate()));
		assertTrue(loadedTransactionDates.contains(transaction1.getDate()));
		assertTrue(loadedTransactionDates.contains(transaction1.getDate()));
		assertTrue(loadedTransactionDates.contains(transaction1.getDate()));
	}
}
