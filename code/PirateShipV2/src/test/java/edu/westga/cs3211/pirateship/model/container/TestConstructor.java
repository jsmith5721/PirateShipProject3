package edu.westga.cs3211.pirateship.model.container;

import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.StockType;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;

public class TestConstructor {

    @Test
    void testUserInputConstructorSizeZeroThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Container(0, new ArrayList<>(), StockType.OTHER);
        });
    }

    @Test
    void testUserInputConstructorNegativeSizeThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Container(-5, new ArrayList<>(), StockType.OTHER);
        });
    }

    @Test
    void testUserInputConstructorValid() {
        Container container = new Container(10, new ArrayList<>(), StockType.OTHER);
        
        assertEquals(10, container.getSize());
    }

    @Test
    void testLoadedDataConstructorSizeZeroThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Container(0, new ArrayList<>(), 1, new ArrayList<>(), StockType.OTHER);
        });
    }

    @Test
    void testLoadedDataConstructorNegativeSizeThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Container(-10, new ArrayList<>(), 1, new ArrayList<>(), StockType.OTHER);
        });
    }

    @Test
    void testLoadedDataConstructorIdZeroThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Container(10, new ArrayList<>(), 0, new ArrayList<>(), StockType.OTHER);
        });
    }

    @Test
    void testLoadedDataConstructorNegativeIdThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Container(10, new ArrayList<>(), -3, new ArrayList<>(), StockType.OTHER);
        });
    }
    
    @Test
    void testLoadedDataConstructorNullStockTypeThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Container(10, new ArrayList<>(), 10, new ArrayList<>(), null);
        });
    }

    @Test
    void testLoadedDataConstructorValidWithNullCollections() {
        Container container = new Container(10, null, 5, null, StockType.OTHER);
        
        assertEquals(10, container.getSize());
        assertEquals(5, container.getContainerID());
    }
    
    @Test
    void testLoadedDataConstructorValidWithValidCollections() {
        Container container = new Container(10, new ArrayList<>(), 5, new ArrayList<>(), StockType.OTHER);
        
        assertEquals(10, container.getSize());
        assertEquals(5, container.getContainerID());
    }
}
