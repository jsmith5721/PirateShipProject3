package edu.westga.cs3211.pirateship.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.Transaction;
import edu.westga.cs3211.pirateship.model.User;

public class TestConstructor {
	@Test
    void testConstructorThrowsWhenSpecialQualitiesNull() {
        User user = new User("Jack", "jack", "pw", Roles.CREWMATE);

        assertThrows(NullPointerException.class, () -> {
            new Transaction(new Date(), "Fish", 2, user, null);
        });
    }

    @Test
    void testConstructorCreatesTransactionWithEmptySpecialQualities() {
        User user = new User("Jack", "jack", "pw", Roles.CREWMATE);
        ArrayList<SpecialQualities> qualities = new ArrayList<>();
        Date date = new Date(100000);

        Transaction tx = new Transaction(date, "Fish", 2, user, qualities);

        assertEquals(date, tx.getDate());
        assertEquals("Fish", tx.getStockName());
        assertEquals(2, tx.getQuantity());
        assertEquals(user, tx.getCrewmember());
        assertTrue(tx.getSpecialQualitiesString().isEmpty());
    }

    @Test
    void testConstructorCreatesTransactionWithSpecialQualities() {
        User user = new User("Jack", "jack", "pw", Roles.CREWMATE);
        ArrayList<SpecialQualities> qualities = new ArrayList<>();
        qualities.add(SpecialQualities.FRAGILE);
        qualities.add(SpecialQualities.VALUABLE);
        Date date = new Date(100000);
        
        Transaction tx = new Transaction(date, "Gold", 1, user, qualities);

        String result = tx.getSpecialQualitiesString();

        assertEquals(date, tx.getDate());
        assertEquals(2, tx.getSpecialQualities().size());
        assertTrue(result.contains("FRAGILE"));
        assertTrue(result.contains("VALUABLE"));
        assertTrue(result.endsWith(", "));
    }
}
