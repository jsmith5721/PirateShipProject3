package edu.westga.cs3211.pirateship.viewmodel.loginvm;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.Authenticator;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.viewmodel.LoginVM;

public class TestLogin {

    public static class AuthenticatorDouble extends Authenticator {
        public static User userToReturn = null;

        public static User authenticate(String username, String password, java.util.Collection<User> crew) {
            return userToReturn;
        }
    }

    @Test
    void testLoginThrowsWhenUsernameNull() {
        LoginVM vm = new LoginVM();
        vm.usernameProperty().set(null);
        vm.passwordProperty().set("pw");

        assertThrows(IllegalArgumentException.class, () -> vm.login());
    }
    
    @Test
    void testLoginThrowsWhenUsernameBlank() {
        LoginVM vm = new LoginVM();
        vm.usernameProperty().set("");
        vm.passwordProperty().set("pw");

        assertThrows(IllegalArgumentException.class, () -> vm.login());
    }

    @Test
    void testLoginThrowsWhenPasswordBlank() {
        LoginVM vm = new LoginVM();
        vm.usernameProperty().set("bob");
        vm.passwordProperty().set("");

        assertThrows(IllegalArgumentException.class, () -> vm.login());
    }
    
    @Test
    void testLoginThrowsWhenPasswordNull() {
        LoginVM vm = new LoginVM();
        vm.usernameProperty().set("bob");
        vm.passwordProperty().set(null);

        assertThrows(IllegalArgumentException.class, () -> vm.login());
    }

    @Test
    void testLoginReturnsTrueWhenAuthenticated() {
        LoginVM vm = new LoginVM();
        Ship ship = vm.getShip();

        User goodUser = new User("Bob", "bob", "pw", Roles.CREWMATE);
        ship.getCrew().add(goodUser);

        vm.usernameProperty().set("bob");
        vm.passwordProperty().set("pw");

        AuthenticatorDouble.userToReturn = goodUser;

        boolean result = vm.login();

        assertTrue(result);
    }

    @Test
    void testLoginReturnsFalseWhenNotAuthenticated() {
        LoginVM vm = new LoginVM();

        vm.usernameProperty().set("x");
        vm.passwordProperty().set("y");

        AuthenticatorDouble.userToReturn = null;

        boolean result = vm.login();

        assertFalse(result);
    }
}
