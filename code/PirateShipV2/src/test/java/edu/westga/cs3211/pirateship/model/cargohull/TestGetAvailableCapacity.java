package edu.westga.cs3211.pirateship.model.cargohull;

import edu.westga.cs3211.pirateship.model.CargoHull;
import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.Stock;
import edu.westga.cs3211.pirateship.model.Conditions;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGetAvailableCapacity {

    @Test
    public void testFullCapacityEmptyHull() {
        CargoHull hull = new CargoHull(100);
        assertEquals(100, hull.getAvailableCapacity());
    }

    @Test
    public void testReducedCapacity() {
        CargoHull hull = new CargoHull(300);
        Container c1 = new Container(200, new ArrayList<>());
        c1.getStockItems().add(new Stock("X", 2, 10, Conditions.GOOD));
        Container c2 = new Container(200, new ArrayList<>());
        c2.getStockItems().add(new Stock("Y", 5, 5, Conditions.GOOD));
        hull.getContainers().add(c1);
        hull.getContainers().add(c2);
        assertEquals(255, hull.getAvailableCapacity());
    }
}
