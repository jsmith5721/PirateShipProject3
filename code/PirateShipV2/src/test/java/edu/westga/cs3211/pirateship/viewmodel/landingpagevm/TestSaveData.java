package edu.westga.cs3211.pirateship.viewmodel.landingpagevm;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.viewmodel.LandingPageVM;

public class TestSaveData {

    private static class ShipDouble extends Ship {
        boolean saveCalled = false;

        public ShipDouble() {
            super("X", 50);
            this.setCurrentUser(new User("A", "a", "p", Roles.CREWMATE));
        }

        @Override
        public void saveData() {
            this.saveCalled = true;
        }
    }

    @Test
    void testSaveDataCallsShipSaveMethod() {
        ShipDouble ship = new ShipDouble();
        LandingPageVM vm = new LandingPageVM(ship);

        assertDoesNotThrow(() -> vm.saveData());
        assertEquals(true, ship.saveCalled);
    }
}
