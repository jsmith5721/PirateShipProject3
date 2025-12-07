package edu.westga.cs3211.pirateship.model.ship;

import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.Conditions;
import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.Stock;
import edu.westga.cs3211.pirateship.model.User;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestAddStockToContainer {

    @Test
    void testAddStockNullStock() {
        Ship ship = new Ship("Dauntless", 300);

        assertThrows(IllegalArgumentException.class, () -> {
            ship.addStockToContainer(1, null);
        });
    }
    
    @Test
    void testAddStockToContainerThrowsWhenCargoHullFailsToAddStock() {
        Ship ship = new Ship("Queen Anne's Revenge", 100);
        User user = new User("Jack", "jack", "pass", Roles.CREWMATE);
        ship.setCurrentUser(user);

        ArrayList<SpecialQualities> qualities = new ArrayList<>();
        Container container = new Container(5, qualities);
        ship.addContainer(container);

        Stock tooLargeStock = new Stock("Cannons", 10, 10, Conditions.NEW);

        assertThrows(IllegalArgumentException.class, () -> {
            ship.addStockToContainer(container.getContainerID(), tooLargeStock);
        });
    }

    @Test
    void testAddStockNoUserSet() {
        Ship ship = new Ship("Dauntless", 300);

        ArrayList<SpecialQualities> quals = new ArrayList<>();
        Container container = new Container(50, quals);
        ship.addContainer(container);

        Stock stock = new Stock("Rum", 2, 5, Conditions.GOOD);

        assertThrows(IllegalArgumentException.class, () -> {
            ship.addStockToContainer(container.getContainerID(), stock);
        });
    }

    @Test
    void testAddStockContainerNotFound() {
        Ship ship = new Ship("Dauntless", 300);
        User user = new User("Josh", "josh", "pw", Roles.CREWMATE);
        ship.setCurrentUser(user);

        Stock stock = new Stock("Rum", 2, 5, Conditions.GOOD);

        assertThrows(IllegalArgumentException.class, () -> {
            ship.addStockToContainer(999, stock);
        });
    }
    
    @Test
    void testAddStockToContainerSuccessfullyAddsStockAndTransaction() {
        Ship ship = new Ship("Black Pearl", 200);
        User user = new User("Will Turner", "will", "pass", Roles.CREWMATE);
        ship.setCurrentUser(user);

        ArrayList<SpecialQualities> qualities = new ArrayList<>();
        Container container = new Container(100, qualities);
        ship.addContainer(container);

        Stock stock = new Stock("Rum", 5, 5, Conditions.NEW);

        ship.addStockToContainer(container.getContainerID(), stock);

        assertEquals(1, container.getStockItems().size());
        assertEquals(stock, container.getStockItems().iterator().next());
        assertEquals(1, ship.getTransactions().size());
    }
}
