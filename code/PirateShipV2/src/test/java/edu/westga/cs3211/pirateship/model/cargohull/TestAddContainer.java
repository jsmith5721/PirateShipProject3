package edu.westga.cs3211.pirateship.model.cargohull;

import edu.westga.cs3211.pirateship.model.CargoHull;
import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.StockType;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAddContainer {

    @Test
    public void testAddNullThrows() {
        CargoHull hull = new CargoHull(100);
        assertThrows(IllegalArgumentException.class, () -> 
        	hull.addContainer(null)
        );
    }

    @Test
    public void testAddSuccessful() {
        CargoHull hull = new CargoHull(100);
        Container container = new Container(50, new ArrayList<>(), StockType.OTHER);
        hull.addContainer(container);
        assertEquals(1, hull.getContainers().size());
    }

    @Test
    public void testAddNotEnoughCapacityThrows() {
        CargoHull hull = new CargoHull(30);
        Container container = new Container(50, new ArrayList<>(), StockType.OTHER);
        assertThrows(IllegalArgumentException.class, () -> hull.addContainer(container));
    }
}
