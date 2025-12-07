package edu.westga.cs3211.pirateship.model.ship;

import edu.westga.cs3211.pirateship.model.Ship;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestConstructor {

    @Test
    void testConstructorNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Ship(null, 100);
        });
    }

    @Test
    void testConstructorBlankName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Ship("   ", 100);
        });
    }

    @Test
    void testConstructorZeroCapacity() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Ship("Flying Dutchman", 0);
        });
    }

    @Test
    void testConstructorNegativeCapacity() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Ship("Flying Dutchman", -5);
        });
    }
    
    @Test
    void testConstructorValid() {
        Ship ship = new Ship("Flying Dutchman", 500);

        assertEquals("Flying Dutchman", ship.getName());
        assertNotNull(ship.getCargoHull());
        assertEquals(500, ship.getCargoHull().getCapacity());
        assertNotNull(ship.getCrew());
        assertTrue(ship.getCrew().isEmpty());
    }
}
