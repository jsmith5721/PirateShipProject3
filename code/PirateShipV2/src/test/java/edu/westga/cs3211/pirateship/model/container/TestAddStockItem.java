package edu.westga.cs3211.pirateship.model.container;

import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.Stock;
import edu.westga.cs3211.pirateship.model.StockType;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.Conditions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

public class TestAddStockItem {

    private User buildUser() {
        return new User("Test", "user", "pw", Roles.CREWMATE);
    }

    @Test
    void testNotEnoughSpaceThrows() {
        Container container = new Container(5, new ArrayList<>(), StockType.OTHER);
        Stock stock = new Stock("Big", 2, 5, Conditions.GOOD, StockType.OTHER);

        assertThrows(IllegalArgumentException.class, () -> {
            container.addStockItem(stock, buildUser());
        });
    }

    @Test
    void testEnoughSpaceSucceeds() {
        Container container = new Container(20, new ArrayList<>(), StockType.OTHER);
        Stock stock = new Stock("Small", 1, 5, Conditions.GOOD, StockType.OTHER);
        container.addStockItem(stock, buildUser());
        
        boolean match = false;
        if (container.getStockItems().contains(stock)) {
			match = true;
		}
        
        assertTrue(match);
    }
}
