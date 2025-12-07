package edu.westga.cs3211.pirateship.model.ship;

import edu.westga.cs3211.pirateship.model.CargoHull;
import edu.westga.cs3211.pirateship.model.Ship;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertSame;

public class TestSetCargoHull {

    @Test
    void testSetCargoHullNull() {
        Ship ship = new Ship("Black Pearl", 500);

        assertThrows(IllegalArgumentException.class, () -> {
            ship.setCargoHull(null);
        });
    }

    @Test
    void testSetCargoHullValid() {
        Ship ship = new Ship("Black Pearl", 500);
        CargoHull newHull = new CargoHull(300);

        ship.setCargoHull(newHull);

        assertSame(newHull, ship.getCargoHull());
    }
}
