package edu.westga.cs3211.pirateship.model.cargohull;

import edu.westga.cs3211.pirateship.model.CargoHull;
import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.Stock;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.Conditions;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAddStockToContainer {

    private User buildUser() {
        return new User("Tester", "user", "pw", Roles.CREWMATE);
    }

    @Test
    public void testNullStockThrows() {
        CargoHull hull = new CargoHull(200);
        
        assertThrows(IllegalArgumentException.class, () ->
                hull.addStockToContainer(null, 1, buildUser())
        );
    }

    @Test
    public void testNullCrewThrows() {
        CargoHull hull = new CargoHull(200);
        Stock stock = new Stock("X", 1, 1, Conditions.GOOD);
        
        assertThrows(IllegalArgumentException.class, () ->
                hull.addStockToContainer(stock, 1, null)
        );
    }

    @Test
    public void testContainerNotFoundThrows() {
        CargoHull hull = new CargoHull(200);
        Stock stock = new Stock("X", 1, 1, Conditions.GOOD);
        
        assertThrows(IllegalArgumentException.class, () ->
                hull.addStockToContainer(stock, 999, buildUser())
        );
    }

    @Test
    public void testAddTooLargeStockThrows() {
        CargoHull hull = new CargoHull(200);
        Container c = new Container(10, new ArrayList<>());
        hull.addContainer(c);
        Stock tooBig = new Stock("Huge", 100, 10, Conditions.GOOD);
        
        assertThrows(IllegalArgumentException.class, () ->
                hull.addStockToContainer(tooBig, c.getContainerID(), buildUser())
        );
    }

    @Test
    public void testAddValidStockAddsTransaction() {
        CargoHull hull = new CargoHull(200);
        Container c = new Container(200, new ArrayList<>());
        hull.addContainer(c);
        Stock s = new Stock("Apple", 1, 5, Conditions.GOOD);
        hull.addStockToContainer(s, c.getContainerID(), buildUser());
        assertEquals(1, hull.getTransactionHistory().size());
    }
}
