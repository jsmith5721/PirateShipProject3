package edu.westga.cs3211.pirateship.model.authenticator;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.Authenticator;
import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.User;

public class TestAuthenticate {
	Ship ship;
	User user;
	@BeforeEach
	void setUp() {
		Authenticator authenticator = new Authenticator();
		if (authenticator != null) {
			System.out.println("Authenticator instance created");
		}
		ship = new Ship("Black Pearl", 5000);
		user = new User("Jack", "jack", "pw", Roles.CREWMATE);
		ship.addCrewMember(user);
	}
	
	@Test
    void testAuthenticateThrowsWhenUsernameNull() {
        assertThrows(IllegalArgumentException.class, ()->{
        	Authenticator.authenticate(null, "pw", ship.getCrew());
		});
    }

    @Test
    void testAuthenticateThrowsWhenPasswordNull() {
    	assertThrows(IllegalArgumentException.class, ()->{
        	Authenticator.authenticate("jack", null, ship.getCrew());
		});
    }

    @Test
    void testAuthenticateThrowsWhenUsernameEmpty() {
    	assertThrows(IllegalArgumentException.class, ()->{
    		Authenticator.authenticate("", "pw", ship.getCrew());
		});
    }

    @Test
    void testAuthenticateThrowsWhenPasswordEmpty() {
    	assertThrows(IllegalArgumentException.class, ()->{
    		Authenticator.authenticate("jack", "", ship.getCrew());
		});
    }
    
    @Test
    void testAuthenticateThrowsWhenUsersIsNull() {
    	assertThrows(IllegalArgumentException.class, ()->{
    		Authenticator.authenticate("jack", "pw", null);
		});
    }

    @Test
    void testAuthenticateReturnsUserOnSuccess() {
        User result = Authenticator.authenticate("jack", "pw", ship.getCrew());

        assertSame(user, result);
    }

    @Test
    void testAuthenticateReturnsNullWhenWrongUsername() {
        User result = Authenticator.authenticate("wrong", "pw", ship.getCrew());

        assertNull(result);
    }

    @Test
    void testAuthenticateReturnsNullWhenWrongPassword() {
        User result = Authenticator.authenticate("jack", "wrong", ship.getCrew());

        assertNull(result);
    }
}
