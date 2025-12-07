package edu.westga.cs3211.pirateship.viewmodel.loginvm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.serializers.UserSerializer;
import edu.westga.cs3211.pirateship.viewmodel.LoginVM;

public class TestConstructor {

    public static class UserSerializerDouble extends UserSerializer {
        public static boolean shouldThrow = false;
        public static boolean loadCalled = false;

        public static void loadUsers(Ship ship) throws IOException {
            loadCalled = true;
            if (shouldThrow) {
                throw new IOException();
            }
        }
    }

    @Test
    void testConstructorLoadsUsers() {
        UserSerializerDouble.shouldThrow = false;
        UserSerializerDouble.loadCalled = false;

        LoginVM vm = new LoginVM();

        assertEquals("", vm.usernameProperty().get());
        assertEquals("", vm.passwordProperty().get());
        assertEquals(true, UserSerializerDouble.loadCalled);
    }

    @Test
    void testConstructorHandlesIOExceptionFromLoadUsers() {
        UserSerializerDouble.shouldThrow = true;
        UserSerializerDouble.loadCalled = false;

        assertDoesNotThrow(() -> new LoginVM());
        assertEquals(true, UserSerializerDouble.loadCalled);
    }
}
