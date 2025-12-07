package edu.westga.cs3211.pirateship.model.cargohull;

import edu.westga.cs3211.pirateship.model.CargoHull;
import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.Stock;
import edu.westga.cs3211.pirateship.model.Conditions;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGetCurrentLoad {

    @Test
    public void testEmptyHull() {
        CargoHull hull = new CargoHull(100);
        assertEquals(0, hull.getCurrentLoad());
    }

    @Test
    public void testLoadWithContainers() {
        CargoHull hull = new CargoHull(500);
        Container c1 = new Container(200, new ArrayList<>());
        c1.getStockItems().add(new Stock("A", 2, 5, Conditions.GOOD));
        Container c2 = new Container(200, new ArrayList<>());
        c2.getStockItems().add(new Stock("B", 3, 4, Conditions.GOOD));
        hull.getContainers().add(c1);
        hull.getContainers().add(c2);
        assertEquals(22, hull.getCurrentLoad());
    }
}
