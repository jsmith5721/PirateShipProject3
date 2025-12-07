package edu.westga.cs3211.pirateship.viewmodel.loginvm;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.serializers.UserSerializer;
import edu.westga.cs3211.pirateship.viewmodel.LoginVM;

public class TestSaveUsers {

    public static class UserSerializerDouble extends UserSerializer {
        public static boolean saveCalled = false;
        public static boolean shouldThrow = false;

        public static void saveUsers(Ship ship) throws IOException {
            saveCalled = true;
            if (shouldThrow) {
                throw new IOException();
            }
        }
    }

    @Test
    void testSaveUsersCallsSerializer() {
        UserSerializerDouble.saveCalled = false;
        UserSerializerDouble.shouldThrow = false;

        LoginVM vm = new LoginVM();

        assertDoesNotThrow(() -> 
        	vm.saveUsers()
        );
        assertEquals(true, UserSerializerDouble.saveCalled);
    }

    @Test
    void testSaveUsersIOExceptionHandled() {
        UserSerializerDouble.saveCalled = false;
        UserSerializerDouble.shouldThrow = true;

        LoginVM vm = new LoginVM();

        assertDoesNotThrow(() -> 
        	vm.saveUsers()
        );
        assertEquals(true, UserSerializerDouble.saveCalled);
    }
}
