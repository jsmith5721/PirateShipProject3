package edu.westga.cs3211.pirateship.viewmodel.loginvm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.viewmodel.LoginVM;

public class TestConstructor {
    @Test
    void testConstructorSuccessful() {
        LoginVM vm = new LoginVM();

        assertEquals(null, vm.getShip().getCurrentUser());
        assertEquals("", vm.usernameProperty().get());
        assertEquals("", vm.passwordProperty().get());
    }
}
