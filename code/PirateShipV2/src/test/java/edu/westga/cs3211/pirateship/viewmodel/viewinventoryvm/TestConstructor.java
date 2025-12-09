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
    void testConstructorSetsShipProperly() {
    	Ship ship = new Ship("Flying Dutchman", 500);
        User user = new User("Emi", "emi", "pw", Roles.COOK);
        ship.setCurrentUser(user);
        ViewInventoryVM vm = new ViewInventoryVM(ship);
        
        assertEquals(ship, vm.getShip());
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
    void testConstructorExceptionWithoutCurrentUser() {
        Ship ship = new Ship("Flying Dutchman", 500);
        
        assertThrows(IllegalArgumentException.class, () -> {
        	ship.setCurrentUser(null);
        });
    }
}
