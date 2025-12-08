package edu.westga.cs3211.pirateship.viewmodel.viewinventoryvm;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.CargoHull;
import edu.westga.cs3211.pirateship.model.Conditions;
import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.Stock;
import edu.westga.cs3211.pirateship.model.StockType;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.viewmodel.ViewInventoryVM;

public class TestLoadEntireInventory {

    private Ship ship;
    private Stock foodStock;
    private Stock ammoStock;
    private Stock otherStock;

    @BeforeEach
    void setup() {
        this.ship = new Ship("Flying Dutchman", 500);
        CargoHull hull = new CargoHull(100);
        this.ship.setCargoHull(hull);

        // No special qualities needed for this test, but we must pass collections
        Collection<SpecialQualities> noQuals = new ArrayList<>();

        // FOOD CONTAINER
        Collection<Stock> foodItems = new ArrayList<>();
        this.foodStock = new Stock("Bread", 5, 2, Conditions.GOOD, StockType.FOOD);
        foodItems.add(this.foodStock);
        Container foodContainer = new Container(50, foodItems, 1, noQuals, StockType.FOOD);

        // AMMO CONTAINER
        Collection<Stock> ammoItems = new ArrayList<>();
        this.ammoStock = new Stock("Cannonball", 2, 10, Conditions.NEW, StockType.AMMUNITION);
        ammoItems.add(this.ammoStock);
        Container ammoContainer = new Container(50, ammoItems, 2, noQuals, StockType.AMMUNITION);

        // OTHER CONTAINER
        Collection<Stock> otherItems = new ArrayList<>();
        this.otherStock = new Stock("Rope", 10, 1, Conditions.POOR, StockType.OTHER);
        otherItems.add(this.otherStock);
        Container otherContainer = new Container(50, otherItems, 3, noQuals, StockType.OTHER);

        hull.addContainer(foodContainer);
        hull.addContainer(ammoContainer);
        hull.addContainer(otherContainer);
    }

    @Test
    void testQuartermasterSeesAllStock() {
        this.ship.setCurrentUser(new User("QM", "qm", "pw", Roles.QUARTERMASTER));

        ViewInventoryVM vm = new ViewInventoryVM(this.ship);
        vm.loadEntireInventory(this.ship);

        assertEquals(3, vm.getStocks().size());
        assertTrue(vm.getStocks().contains(this.foodStock));
        assertTrue(vm.getStocks().contains(this.ammoStock));
        assertTrue(vm.getStocks().contains(this.otherStock));
    }

    @Test
    void testCookSeesOnlyFoodStock() {
        this.ship.setCurrentUser(new User("Cook", "cook", "pw", Roles.COOK));

        ViewInventoryVM vm = new ViewInventoryVM(this.ship);
        vm.loadEntireInventory(this.ship);

        assertEquals(1, vm.getStocks().size());
        assertEquals(this.foodStock, vm.getStocks().get(0));
    }

    @Test
    void testOfficerSeesOnlyAmmoStock() {
        this.ship.setCurrentUser(new User("Officer", "off", "pw", Roles.OFFICER));

        ViewInventoryVM vm = new ViewInventoryVM(this.ship);
        vm.loadEntireInventory(this.ship);

        assertEquals(1, vm.getStocks().size());
        assertEquals(this.ammoStock, vm.getStocks().get(0));
    }

    @Test
    void testLoadEntireInventoryWithNullShipDoesNothing() {
        this.ship.setCurrentUser(new User("QM", "qm", "pw", Roles.QUARTERMASTER));

        ViewInventoryVM vm = new ViewInventoryVM(this.ship);
        vm.loadEntireInventory(null);

        assertEquals(0, vm.getStocks().size());
    }

    @Test
    void testLoadEntireInventoryWithNoContainersDoesNothing() {
        this.ship.setCurrentUser(new User("QM", "qm", "pw", Roles.QUARTERMASTER));

        this.ship.getCargoHull().getContainers().clear();

        ViewInventoryVM vm = new ViewInventoryVM(this.ship);
        vm.loadEntireInventory(this.ship);

        assertEquals(0, vm.getStocks().size());
    }

}
