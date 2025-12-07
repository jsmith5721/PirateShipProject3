package edu.westga.cs3211.pirateship.viewmodel.landingpagevm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.CargoHull;
import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.Transaction;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.model.serializers.CargoSerializer;
import edu.westga.cs3211.pirateship.model.serializers.TransactionSerializer;
import edu.westga.cs3211.pirateship.viewmodel.LandingPageVM;

public class TestConstructor {

    private static class CargoSerializerDouble extends CargoSerializer {
        static CargoHull hullToReturn = null;
        static boolean shouldThrow = false;

        public static CargoHull loadCargo() throws IOException {
            if (shouldThrow) {
                throw new IOException();
            }
            return hullToReturn;
        }
    }

    private static class TransactionSerializerDouble extends TransactionSerializer {
        static List<Transaction> transactionsToReturn = null;
        static boolean shouldThrow = false;

        public static List<Transaction> loadTransactionHistory() throws IOException {
            if (shouldThrow) {
                throw new IOException();
            }
            return transactionsToReturn;
        }
    }

    @Test
    void testConstructorSetsWelcomeMessageAndReviewPermission() {
        Ship ship = new Ship("Test", 200);
        User user = new User("Bob", "bob", "pw", Roles.QUARTERMASTER);
        ship.setCurrentUser(user);

        CargoSerializerDouble.hullToReturn = new CargoHull(300);
        TransactionSerializerDouble.transactionsToReturn = new ArrayList<>();

        LandingPageVM vm = new LandingPageVM(ship);

        assertEquals("Welcome, Bob", vm.welcomeMessageProperty().get());
        assertTrue(vm.canReviewStockChangesProperty().get());
    }

    @Test
    void testConstructorLoadsCargoIntoShip() {
        Ship ship = new Ship("Ship", 100);
        User user = new User("A", "a", "p", Roles.CREWMATE);
        ship.setCurrentUser(user);

        CargoHull loadedHull = new CargoHull(400);
        CargoSerializerDouble.hullToReturn = loadedHull;
        TransactionSerializerDouble.transactionsToReturn = new ArrayList<>();

        LandingPageVM vm = new LandingPageVM(ship);

        assertEquals(100, vm.getShip().getCargoHull().getCapacity());
    }

    @Test
    void testConstructorLoadsTransactionsIntoShip() {
        Ship ship = new Ship("Ship", 100);
        ship.setCurrentUser(new User("Test", "t", "p", Roles.CREWMATE));

        CargoSerializerDouble.hullToReturn = new CargoHull(500);

        List<Transaction> list = new ArrayList<>();
        list.add(new Transaction(new java.util.Date(), "Item", 3, 
                new User("U", "u", "p", Roles.CREWMATE), new ArrayList<>()));

        TransactionSerializerDouble.transactionsToReturn = list;

        LandingPageVM vm = new LandingPageVM(ship);

        assertEquals(0, vm.getShip().getCargoHull().getTransactionHistory().size());
    }

    @Test
    void testConstructorHandlesCargoIOException() {
        Ship ship = new Ship("Ship", 100);
        ship.setCurrentUser(new User("X", "x", "p", Roles.CREWMATE));

        CargoSerializerDouble.shouldThrow = true;
        TransactionSerializerDouble.transactionsToReturn = new ArrayList<>();

        LandingPageVM vm = new LandingPageVM(ship);

        assertEquals(100, vm.getShip().getCargoHull().getCapacity());
    }

    @Test
    void testConstructorHandlesTransactionIOException() {
        Ship ship = new Ship("Ship", 100);
        ship.setCurrentUser(new User("X", "x", "p", Roles.CREWMATE));

        CargoSerializerDouble.hullToReturn = new CargoHull(500);
        TransactionSerializerDouble.shouldThrow = true;

        LandingPageVM vm = new LandingPageVM(ship);

        assertEquals(0, vm.getShip().getCargoHull().getTransactionHistory().size());
    }
}
