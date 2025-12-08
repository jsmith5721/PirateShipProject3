package edu.westga.cs3211.pirateship.model.container;

import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.StockType;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;

public class TestAddSpecialQualites {

    @Test
    void testAddSpecialQualityNullQuality() {
        Container container = new Container(10, new ArrayList<>(), StockType.OTHER);

        assertThrows(IllegalArgumentException.class, () -> {
            container.addSpecialQuality(null);
        });
    }

    @Test
    void testAddDuplicateQualityThrows() {
        ArrayList<SpecialQualities> qualities = new ArrayList<>();
        qualities.add(SpecialQualities.FRAGILE);
        Container container = new Container(10, qualities, StockType.OTHER);

        assertThrows(IllegalArgumentException.class, () -> {
            container.addSpecialQuality(SpecialQualities.FRAGILE);
        });
    }

    @Test
    void testAddValidQuality() {
        Container container = new Container(10, new ArrayList<>(), StockType.OTHER);
        container.addSpecialQuality(SpecialQualities.LIQUID);
        
        assertEquals(1, container.getSpecialQualities().size());
    }
}
