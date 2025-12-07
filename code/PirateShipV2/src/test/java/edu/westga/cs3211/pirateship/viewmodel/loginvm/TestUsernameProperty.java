package edu.westga.cs3211.pirateship.viewmodel.loginvm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.viewmodel.LoginVM;

public class TestUsernameProperty {

    @Test
    void testUsernamePropertyStoresValue() {
        LoginVM vm = new LoginVM();
        vm.usernameProperty().set("bob");

        assertEquals("bob", vm.usernameProperty().get());
    }
}
