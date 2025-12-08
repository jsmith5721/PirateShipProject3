package edu.westga.cs3211.pirateship.model.container;

import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.Stock;
import edu.westga.cs3211.pirateship.model.StockType;
import edu.westga.cs3211.pirateship.model.Conditions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;

public class TestCalculateCurrentLoad {

    @Test
    void testEmptyContainerHasZeroLoad() {
        Container container = new Container(10, new ArrayList<>(), StockType.OTHER);
        
        assertEquals(0, container.calculateCurrentLoad());
    }

    @Test
    void testLoadComputesCorrectly() {
        Container container = new Container(100, new ArrayList<>(), StockType.OTHER);
        container.getStockItems().add(new Stock("A", 2, 5, Conditions.GOOD, StockType.OTHER));
        container.getStockItems().add(new Stock("B", 1, 7, Conditions.GOOD, StockType.OTHER));
        
        assertEquals(17, container.calculateCurrentLoad());
    }
}
