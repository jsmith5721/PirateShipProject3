package edu.westga.cs3211.pirateship.model.cargohull;

import edu.westga.cs3211.pirateship.model.CargoHull;
import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.Stock;
import edu.westga.cs3211.pirateship.model.StockType;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.Conditions;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAddStockToContainer {
	private User user;
    
	@BeforeEach
	private void setUp() {
        user = new User("Tester", "user", "pw", Roles.CREWMATE);
    }
    

    @Test
    public void testNullStockThrows() {
        CargoHull hull = new CargoHull(200);
        
        assertThrows(IllegalArgumentException.class, () ->
                hull.addStockToContainer(null, 1, this.user)
        );
    }

    @Test
    public void testNullCrewThrows() {
        CargoHull hull = new CargoHull(200);
        Stock stock = new Stock("X", 1, 1, Conditions.GOOD, StockType.OTHER);
        
        assertThrows(IllegalArgumentException.class, () ->
                hull.addStockToContainer(stock, 1, null)
        );
    }

    @Test
    public void testContainerNotFoundThrows() {
        CargoHull hull = new CargoHull(200);
        Stock stock = new Stock("X", 1, 1, Conditions.GOOD, StockType.OTHER);
        
        assertThrows(IllegalArgumentException.class, () ->
                hull.addStockToContainer(stock, 999, this.user)
        );
    }

    @Test
    public void testAddTooLargeStockThrows() {
        CargoHull hull = new CargoHull(200);
        Container container = new Container(10, new ArrayList<>(), StockType.OTHER);
        hull.addContainer(container);
        Stock tooBig = new Stock("Huge", 100, 10, Conditions.GOOD, StockType.OTHER);
        
        assertThrows(IllegalArgumentException.class, () ->
                hull.addStockToContainer(tooBig, container.getContainerID(), this.user)
        );
    }

    @Test
    public void testAddValidStockAddsTransaction() {
        CargoHull hull = new CargoHull(200);
        Container container = new Container(200, new ArrayList<>(), StockType.FOOD);
        hull.addContainer(container);
        Stock s = new Stock("Apple", 1, 5, Conditions.GOOD, StockType.FOOD);
        hull.addStockToContainer(s, container.getContainerID(), this.user);
        assertEquals(1, hull.getTransactionHistory().size());
    }
}
