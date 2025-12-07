package edu.westga.cs3211.pirateship.model.ship;

import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestAddCrewMember {

    @Test
    void testAddCrewMemberNull() {
        Ship ship = new Ship("Interceptor", 250);

        assertThrows(IllegalArgumentException.class, () -> {
            ship.addCrewMember(null);
        });
    }

    @Test
    void testAddCrewMemberValid() {
        Ship ship = new Ship("Interceptor", 250);
        User user = new User("Will", "will", "pw", Roles.CREWMATE);

        ship.addCrewMember(user);

        assertTrue(ship.getCrew().contains(user));
    }
}
