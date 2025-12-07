package edu.westga.cs3211.pirateship.model.ship;

import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestSetCurrentUser {

    @Test
    void testSetCurrentUserNull() {
        Ship ship = new Ship("Queen Anne's Revenge", 500);

        assertThrows(IllegalArgumentException.class, () -> {
            ship.setCurrentUser(null);
        });
    }

    @Test
    void testSetCurrentUserValid() {
        Ship ship = new Ship("Queen Anne's Revenge", 500);
        User user = new User("Jack", "jack", "pw", Roles.CREWMATE);

        ship.setCurrentUser(user);

        assertSame(user, ship.getCurrentUser());
    }
}
