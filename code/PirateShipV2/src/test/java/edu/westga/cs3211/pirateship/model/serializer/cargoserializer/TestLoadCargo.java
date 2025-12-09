package edu.westga.cs3211.pirateship.model.serializer.cargoserializer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import edu.westga.cs3211.pirateship.model.serializers.CargoSerializer;

public class TestLoadCargo {
	Ship ship;
	User user;
	Container container1;
	Container container2;
	Container container3;
	Container container4;
	Container container5;
	Container container6;
	
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
		
		List<Stock> noQuals = new ArrayList<Stock>();
		Stock stock5 = new Stock("Wood", 500, 4, new ArrayList<SpecialQualities>(), Conditions.FAIR, StockType.OTHER);
		noQuals.add(stock5);
		
		List<Stock> missingExpiration = new ArrayList<Stock>();
		Stock stock6 = new Stock("Cheese", 150, 2, qualitiesParish, Conditions.GOOD, StockType.FOOD);
		missingExpiration.add(stock6);
		
		container1 = new Container(500, stockParish, 1, qualitiesParish, StockType.FOOD);
		container2 = new Container(300, stockFragAndExplo, 2, qualitiesFragAndExplo, StockType.AMMUNITION);
		container3 = new Container(400, stockValuable, 3, qualitiesValuable, StockType.OTHER);
		container4 = new Container(200, new ArrayList<Stock>(), 4, qualitiesValuable, StockType.OTHER);
		container5 = new Container(600, noQuals, 5, new ArrayList<SpecialQualities>(), StockType.OTHER);
		container6 = new Container(350, missingExpiration, 6, qualitiesParish, StockType.FOOD);
	}
	
	@Test
	public void testLoadCargoNoContainers() {
		CargoSerializer.saveCargo(ship, "TestCargo.txt");
		Ship loadedShip = CargoSerializer.loadCargo("TestCargo.txt");
		
		assertEquals("Black Pearl", loadedShip.getName());
		assertEquals(10000, loadedShip.getCargoHull().getCapacity());
		assertEquals(0, loadedShip.getContainers().size());
	}
	
	@Test
	public void testLoadCargoContainerNoQals() {
		this.ship.addContainer(container5);
		
		CargoSerializer.saveCargo(ship, "TestCargo.txt");
		Ship loadedShip = CargoSerializer.loadCargo("TestCargo.txt");
		
		assertEquals("Black Pearl", loadedShip.getName());
		assertEquals(10000, loadedShip.getCargoHull().getCapacity());
		assertEquals(1, loadedShip.getContainers().size());
		assertEquals(5, loadedShip.getContainers().get(0).getContainerID());
	}
	
	@Test
	public void testLoadCargoContainerNoStock() {
		this.ship.addContainer(container4);
		
		CargoSerializer.saveCargo(ship, "TestCargo.txt");
		Ship loadedShip = CargoSerializer.loadCargo("TestCargo.txt");
		
		assertEquals("Black Pearl", loadedShip.getName());
		assertEquals(10000, loadedShip.getCargoHull().getCapacity());
		assertEquals(1, loadedShip.getContainers().size());
		assertEquals(4, loadedShip.getContainers().get(0).getContainerID());
	}
	
	
	@Test
	public void testLoadCargoWithCrewCargoAndTransactions() {
		ship.getContainers().add(container1);
		ship.getContainers().add(container2);
		ship.getContainers().add(container3);
		
		CargoSerializer.saveCargo(ship, "TestCargo.txt");
		Ship loadedShip = CargoSerializer.loadCargo("TestCargo.txt");
		
		ArrayList<Integer> loadedContainerIds = new ArrayList<Integer>();
		ArrayList<String> loadedStockNames = new ArrayList<String>();
		for (Container container : loadedShip.getContainers()) {
			loadedContainerIds.add(container.getContainerID());
			for (Stock stock : container.getStockItems()) {
				loadedStockNames.add(stock.getName());
			}
		}
		
		
		assertEquals("Black Pearl", loadedShip.getName());
		assertEquals(10000, loadedShip.getCargoHull().getCapacity());
		assertTrue(loadedContainerIds.contains(container1.getContainerID()));
		assertTrue(loadedContainerIds.contains(container1.getContainerID()));
		assertTrue(loadedContainerIds.contains(container1.getContainerID()));
		assertTrue(loadedStockNames.contains("Fruit"));
		assertTrue(loadedStockNames.contains("Ammo"));
		assertTrue(loadedStockNames.contains("Gold Coins"));
		assertTrue(loadedStockNames.contains("Gems"));
	}
	
	@Test
	public void testLoadCargoContainerMissingExpirationDate() {
		this.ship.addContainer(container6);
		
		CargoSerializer.saveCargo(ship, "TestCargo.txt");
		assertThrows(IllegalArgumentException.class, () -> {
			CargoSerializer.loadCargo("TestCargo.txt");
		});
	}
	
	@Test
	public void testLoadCargoFileNotFound() {
		Ship ship = CargoSerializer.loadCargo("NonExistentFile.txt");
		assertTrue(ship.getName().equals("Flying Duchman"));
		assertEquals(5000, ship.getCargoHull().getCapacity());
	}
	
	
}