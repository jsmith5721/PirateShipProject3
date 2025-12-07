package edu.westga.cs3211.pirateship.model.cargohull;

import edu.westga.cs3211.pirateship.model.CargoHull;
import edu.westga.cs3211.pirateship.model.Container;
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
        Container c = new Container(50, new ArrayList<>());
        hull.addContainer(c);
        assertEquals(1, hull.getContainers().size());
    }

    @Test
    public void testAddNotEnoughCapacityThrows() {
        CargoHull hull = new CargoHull(30);
        Container c = new Container(50, new ArrayList<>());
        assertThrows(IllegalArgumentException.class, () -> hull.addContainer(c));
    }
}
