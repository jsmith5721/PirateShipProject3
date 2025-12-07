package edu.westga.cs3211.pirateship.model.ship;

import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.CargoHull;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.model.Roles;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;

public class TestSaveData {

    @Test
    void testSaveDataSuccess() {
        TestCargoSerializer.setFailMode(false);
        TestTransactionSerializer.setFailMode(false);

        Ship ship = new Ship("Black Pearl", 100);
        User user = new User("Jack", "jack", "pw", Roles.CREWMATE);
        ship.setCurrentUser(user);

        assertDoesNotThrow(() -> {
            ship.saveData();
        });
    }

    @Test
    void testSaveDataFailure() {
        TestCargoSerializer.setFailMode(true);
        TestTransactionSerializer.setFailMode(true);

        Ship ship = new Ship("Flying Dutchman", 50);
        User user = new User("Jones", "dj", "pw", Roles.CREWMATE);
        ship.setCurrentUser(user);

        assertDoesNotThrow(() -> {
            ship.saveData();
        });
    }

    public static class CargoSerializer {
        public static void serializeCargoToFile(CargoHull hull) throws IOException {
            if (TestCargoSerializer.failMode) {
                throw new IOException("Failure");
            }
        }
    }

    public static class TransactionSerializer {
        public static void serializeTransactionHistoryToFile(CargoHull hull) throws IOException {
            if (TestTransactionSerializer.failMode) {
                throw new IOException("Failure");
            }
        }
    }

    private static class TestCargoSerializer {
        private static boolean failMode = false;
        public static void setFailMode(boolean value) {
            failMode = value;
        }
    }

    private static class TestTransactionSerializer {
        private static boolean failMode = false;
        public static void setFailMode(boolean value) {
            failMode = value;
        }
    }
}
