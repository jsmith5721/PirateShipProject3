package edu.westga.cs3211.pirateship.viewmodel.viewinventoryvm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.viewmodel.ViewInventoryVM;

public class TestConstructor {

    @Test
    void testConstructorRejectsNullShip() {
        assertThrows(IllegalArgumentException.class, () -> new ViewInventoryVM(null));
    }

    @Test
    void testConstructorWelcomeMessageWithCurrentUser() {
        Ship ship = new Ship("Flying Dutchman", 500);
        User user = new User("Emi", "emi", "pw", Roles.COOK);
        ship.setCurrentUser(user);

        ViewInventoryVM vm = new ViewInventoryVM(ship);

        assertEquals("Welcome, Emi", vm.welcomeMessageProperty().get());
    }

    @Test
    void testConstructorWelcomeMessageWithoutCurrentUser() {
        Ship ship = new Ship("Flying Dutchman", 500);
        ship.setCurrentUser(null);

        ViewInventoryVM vm = new ViewInventoryVM(ship);

        assertEquals("Welcome", vm.welcomeMessageProperty().get());
    }
}
