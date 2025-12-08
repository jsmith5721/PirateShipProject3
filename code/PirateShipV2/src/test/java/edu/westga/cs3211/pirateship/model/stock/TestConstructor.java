package edu.westga.cs3211.pirateship.model.stock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.Conditions;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.Stock;
import edu.westga.cs3211.pirateship.model.StockType;

public class TestConstructor {
	@Test
    void testBasicConstructorThrowsWhenNameNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock(null, 1, 1, Conditions.GOOD, StockType.OTHER);
        });
    }

    @Test
    void testBasicConstructorThrowsWhenNameBlank() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock("   ", 1, 1, Conditions.GOOD, StockType.OTHER);
        });
    }

    @Test
    void testBasicConstructorThrowsWhenConditionNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock("Fish", 1, 1, null, StockType.FOOD);
        });
    }

    @Test
    void testBasicConstructorThrowsWhenQuantityZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock("Fish", 0, 1, Conditions.GOOD, StockType.FOOD);
        });
    }

    @Test
    void testBasicConstructorThrowsWhenQuantityNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock("Fish", -1, 1, Conditions.GOOD, StockType.FOOD);
        });
    }

    @Test
    void testBasicConstructorThrowsWhenSizeZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock("Fish", 1, 0, Conditions.GOOD, StockType.FOOD);
        });
    }

    @Test
    void testBasicConstructorThrowsWhenSizeNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock("Fish", 1, -2, Conditions.GOOD, StockType.FOOD);
        });
    }

    @Test
    void testQualitiesConstructorThrowsWhenNameNull() {
        Collection<SpecialQualities> quals = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock(null, 1, 1, quals, Conditions.GOOD, StockType.OTHER);
        });
    }

    @Test
    void testQualitiesConstructorThrowsWhenNameBlank() {
        Collection<SpecialQualities> quals = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock(" ", 1, 1, quals, Conditions.GOOD, StockType.OTHER);
        });
    }

    @Test
    void testQualitiesConstructorThrowsWhenSpecialQualitiesNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock("Fish", 1, 1, null, Conditions.GOOD, StockType.FOOD);
        });
    }

    @Test
    void testQualitiesConstructorThrowsWhenConditionNull() {
        Collection<SpecialQualities> quals = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock("Fish", 1, 1, quals, null, StockType.FOOD);
        });
    }

    @Test
    void testQualitiesConstructorThrowsWhenQuantityZero() {
        Collection<SpecialQualities> quals = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock("Fish", 0, 1, quals, Conditions.GOOD, StockType.FOOD);
        });
    }

    @Test
    void testQualitiesConstructorThrowsWhenQuantityNegative() {
        Collection<SpecialQualities> quals = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock("Fish", -5, 1, quals, Conditions.GOOD, StockType.FOOD);
        });
    }

    @Test
    void testQualitiesConstructorThrowsWhenSizeZero() {
        Collection<SpecialQualities> quals = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock("Fish", 1, 0, quals, Conditions.GOOD, StockType.FOOD);
        });
    }

    @Test
    void testQualitiesConstructorThrowsWhenSizeNegative() {
        Collection<SpecialQualities> quals = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock("Fish", 1, -2, quals, Conditions.GOOD, StockType.FOOD);
        });
    }

    @Test
    void testExpirationConstructorThrowsWhenNameNull() {
        Collection<SpecialQualities> quals = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock(null, 1, 1, quals, Conditions.GOOD, StockType.OTHER, LocalDate.now().plusDays(1));
        });
    }

    @Test
    void testExpirationConstructorThrowsWhenNameBlank() {
        Collection<SpecialQualities> quals = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock(" ", 1, 1, quals, Conditions.GOOD, StockType.OTHER, LocalDate.now().plusDays(1));
        });
    }

    @Test
    void testExpirationConstructorThrowsWhenSpecialQualitiesNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock("Fish", 1, 1, null, Conditions.GOOD, StockType.FOOD, LocalDate.now().plusDays(1));
        });
    }

    @Test
    void testExpirationConstructorThrowsWhenConditionNull() {
        Collection<SpecialQualities> quals = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock("Fish", 1, 1, quals, null, StockType.FOOD, LocalDate.now().plusDays(1));
        });
    }

    @Test
    void testExpirationConstructorThrowsWhenExpirationNull() {
        Collection<SpecialQualities> quals = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock("Fish", 1, 1, quals, Conditions.GOOD, StockType.FOOD, null);
        });
    }

    @Test
    void testExpirationConstructorThrowsWhenExpirationInPast() {
        Collection<SpecialQualities> quals = new ArrayList<>();
        LocalDate past = LocalDate.now().minusDays(1);

        assertThrows(IllegalArgumentException.class, () -> {
            new Stock("Fish", 1, 1, quals, Conditions.GOOD, StockType.FOOD, past);
        });
    }

    @Test
    void testExpirationConstructorThrowsWhenQuantityZero() {
        Collection<SpecialQualities> quals = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock("Fish", 0, 1, quals, Conditions.GOOD, StockType.FOOD, LocalDate.now().plusDays(1));
        });
    }

    @Test
    void testExpirationConstructorThrowsWhenSizeZero() {
        Collection<SpecialQualities> quals = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock("Fish", 1, 0, quals, Conditions.GOOD, StockType.FOOD, LocalDate.now().plusDays(1));
        });
    }
    
    @Test
    void testNoQualitiesConstructorWithNullStockTypeThrows() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Stock("Fish", 1, 1, Conditions.GOOD, null);
		});
	}
    
    @Test
    void testQualitiesConstructorWithNullStockTypeThrows() {
		Collection<SpecialQualities> quals = new ArrayList<>();
		assertThrows(IllegalArgumentException.class, () -> {
			new Stock("Fish", 1, 1, quals, Conditions.GOOD, null);
		});
		}
	
	@Test
	void testExpirationConstructorWithNullStockTypeThrows() {
		Collection<SpecialQualities> quals = new ArrayList<>();
		quals.add(SpecialQualities.PARISHABLE);
		assertThrows(IllegalArgumentException.class, () -> {
			new Stock("Fish", 1, 1, quals, Conditions.GOOD, null, LocalDate.now().plusDays(1));
		});
	}
	
	@Test
    void testBasicConstructorInitializesFieldsCorrectly() {
        Stock stock = new Stock("Fish", 2, 3, Conditions.GOOD, StockType.FOOD);

        assertEquals("Fish", stock.getName());
        assertEquals(2, stock.getQuantity());
        assertEquals(6, stock.getTotalSize());
        assertEquals(Conditions.GOOD, stock.getCondition());
        assertNotNull(stock.getSpecialQualities());
        assertTrue(stock.getSpecialQualities().isEmpty());
        assertEquals(StockType.FOOD, stock.getStockType());
        assertNull(stock.getExpirationDate());
    }
	
	@Test
    void testConstructorWithQualitiesInitializesFieldsCorrectly() {
        Collection<SpecialQualities> qualities = new ArrayList<>();
        qualities.add(SpecialQualities.FRAGILE);
        qualities.add(SpecialQualities.VALUABLE);

        Stock stock = new Stock("Gold", 5, 2, qualities, Conditions.NEW, StockType.OTHER);

        assertEquals("Gold", stock.getName());
        assertEquals(5, stock.getQuantity());
        assertEquals(10, stock.getTotalSize()); 
        assertEquals(Conditions.NEW, stock.getCondition());
        assertSame(qualities, stock.getSpecialQualities());
        assertEquals(StockType.OTHER, stock.getStockType());
        assertNull(stock.getExpirationDate());
    }
	
	@Test
    void testFullConstructorInitializesFieldsCorrectly() {
        Collection<SpecialQualities> qualities = new ArrayList<>();
        qualities.add(SpecialQualities.PARISHABLE);

        LocalDate expiration = LocalDate.of(2026, 5, 1);

        Stock stock = new Stock("Food", 4, 2, qualities, Conditions.GOOD, StockType.FOOD, expiration);

        assertEquals("Food", stock.getName());
        assertEquals(4, stock.getQuantity());
        assertEquals(8, stock.getTotalSize());
        assertEquals(Conditions.GOOD, stock.getCondition());
        assertSame(qualities, stock.getSpecialQualities());
        assertEquals(StockType.FOOD, stock.getStockType());
        assertEquals(expiration, stock.getExpirationDate());
    }
}
