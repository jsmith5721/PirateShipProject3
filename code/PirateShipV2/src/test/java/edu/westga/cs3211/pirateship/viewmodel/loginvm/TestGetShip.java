package edu.westga.cs3211.pirateship.viewmodel.loginvm;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.viewmodel.LoginVM;

public class TestGetShip {

    @Test
    void testGetShipReturnsShip() {
        LoginVM vm = new LoginVM();
        assertNotNull(vm.getShip());
    }
}
