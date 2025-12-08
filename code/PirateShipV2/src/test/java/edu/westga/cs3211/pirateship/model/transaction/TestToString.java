package edu.westga.cs3211.pirateship.model.transaction;

import edu.westga.cs3211.pirateship.model.Transaction;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.SpecialQualities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;

public class TestToString {

    @Test
    void testToStringProducesExpectedContent() {
        Date date = new Date(0);
        User user = new User("Jack Sparrow", "jack", "pw", Roles.CREWMATE);

        ArrayList<SpecialQualities> qualities = new ArrayList<>();
        qualities.add(SpecialQualities.FRAGILE);
        qualities.add(SpecialQualities.VALUABLE);

        Transaction transaction = new Transaction(date, "Rum Barrel", 5, user, qualities);

        String result = transaction.toString();

        assertTrue(result.contains("Date:"));
        assertTrue(result.contains("Wed Dec 31"));
        assertTrue(result.contains("Stock Name: Rum Barrel"));
        assertTrue(result.contains("Quantity: 5"));
        assertTrue(result.contains("Crewmember: jack"));
        assertTrue(result.contains("Special Qualities: FRAGILE, VALUABLE, "));
    }
}
