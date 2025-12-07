package edu.westga.cs3211.pirateship.model.cargohull;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.CargoHull;

public class TestConstructor {

    @Test
    public void testValidCapacity() {
        CargoHull hull = new CargoHull(100);
        assertEquals(100, hull.getCapacity());
    }

    @Test
    public void testInvalidCapacityZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CargoHull(0);
        });
    }

    @Test
    public void testInvalidCapacityNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CargoHull(-5);
        });
    }
}
