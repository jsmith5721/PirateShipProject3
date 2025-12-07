package edu.westga.cs3211.pirateship.model.container;

import edu.westga.cs3211.pirateship.model.Container;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;

public class TestConstructor {

    @Test
    void testUserInputConstructorSizeZeroThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Container(0, new ArrayList<>());
        });
    }

    @Test
    void testUserInputConstructorNegativeSizeThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Container(-5, new ArrayList<>());
        });
    }

    @Test
    void testUserInputConstructorValid() {
        Container container = new Container(10, new ArrayList<>());
        
        assertEquals(10, container.getSize());
    }

    @Test
    void testLoadedDataConstructorSizeZeroThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Container(0, new ArrayList<>(), 1, new ArrayList<>());
        });
    }

    @Test
    void testLoadedDataConstructorNegativeSizeThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Container(-10, new ArrayList<>(), 1, new ArrayList<>());
        });
    }

    @Test
    void testLoadedDataConstructorIdZeroThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Container(10, new ArrayList<>(), 0, new ArrayList<>());
        });
    }

    @Test
    void testLoadedDataConstructorNegativeIdThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Container(10, new ArrayList<>(), -3, new ArrayList<>());
        });
    }

    @Test
    void testLoadedDataConstructorValidWithNullCollections() {
        Container container = new Container(10, null, 5, null);
        
        assertEquals(10, container.getSize());
        assertEquals(5, container.getContainerID());
    }
    
    @Test
    void testLoadedDataConstructorValidWithValidCollections() {
        Container container = new Container(10, new ArrayList<>(), 5, new ArrayList<>());
        
        assertEquals(10, container.getSize());
        assertEquals(5, container.getContainerID());
    }
}
