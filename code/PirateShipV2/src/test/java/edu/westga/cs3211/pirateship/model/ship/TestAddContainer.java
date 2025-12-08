package edu.westga.cs3211.pirateship.model.ship;

import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.StockType;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestAddContainer {

    @Test
    void testAddContainerNull() {
        Ship ship = new Ship("Black Pearl", 500);

        assertThrows(IllegalArgumentException.class, () -> {
            ship.addContainer(null);
        });
    }
    
    @Test
    void testAddContainerThrowsWhenCargoHullCannotFitContainer() {
        Ship ship = new Ship("Black Pearl", 10);

        ArrayList<SpecialQualities> qualities = new ArrayList<>();
        qualities.add(SpecialQualities.FRAGILE);

        Container oversizedContainer = new Container(50, qualities, StockType.OTHER);

        assertThrows(IllegalArgumentException.class, () -> {
            ship.addContainer(oversizedContainer);
        });
    }

    @Test
    void testAddContainerValid() {
        Ship ship = new Ship("Black Pearl", 500);
        ArrayList<SpecialQualities> quals = new ArrayList<>();
        Container container = new Container(50, quals, StockType.OTHER);

        ship.addContainer(container);

        assertTrue(ship.getContainers().contains(container));
    }
}
