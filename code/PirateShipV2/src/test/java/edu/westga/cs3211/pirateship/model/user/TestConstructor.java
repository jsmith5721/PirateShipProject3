package edu.westga.cs3211.pirateship.model.user;

import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.model.Roles;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestConstructor {

    @Test
    void testValidConstructor() {
        User user = new User("Jack Sparrow", "jack", "pw", Roles.CREWMATE);

        assertEquals("Jack Sparrow", user.getName());
        assertEquals("jack", user.getUsername());
        assertEquals("pw", user.getPassword());
        assertEquals(Roles.CREWMATE, user.getRole());
    }

    @Test
    void testNullNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User(null, "jack", "pw", Roles.CREWMATE);
        });
    }

    @Test
    void testBlankNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User("   ", "jack", "pw", Roles.CREWMATE);
        });
    }

    @Test
    void testNullUsernameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User("Jack", null, "pw", Roles.CREWMATE);
        });
    }

    @Test
    void testBlankUsernameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User("Jack", "", "pw", Roles.CREWMATE);
        });
    }

    @Test
    void testNullPasswordThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User("Jack", "jack", null, Roles.CREWMATE);
        });
    }

    @Test
    void testBlankPasswordThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User("Jack", "jack", "   ", Roles.CREWMATE);
        });
    }

    @Test
    void testNullRoleThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User("Jack", "jack", "pw", null);
        });
    }
}
