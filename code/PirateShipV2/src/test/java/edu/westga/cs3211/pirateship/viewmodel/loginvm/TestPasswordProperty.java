package edu.westga.cs3211.pirateship.viewmodel.loginvm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.viewmodel.LoginVM;

public class TestPasswordProperty {

    @Test
    void testPasswordPropertyStoresValue() {
        LoginVM vm = new LoginVM();
        vm.passwordProperty().set("secret");

        assertEquals("secret", vm.passwordProperty().get());
    }
}
