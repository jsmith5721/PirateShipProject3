package edu.westga.cs3211.pirateship.viewmodel.stockchangesvm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.Transaction;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.viewmodel.StockChangesVM;

public class TestConstructor {

    private Transaction buildTransaction(String userName) {
        User user = new User(userName, userName, "pw", Roles.CREWMATE);
        ArrayList<SpecialQualities> qualities = new ArrayList<>();
        qualities.add(SpecialQualities.FRAGILE);
        return new Transaction(new Date(), "Stock", 1, user, qualities);
    }

    @Test
    void testConstructorInitializesAllFieldsCorrectly() {
        Transaction t1 = buildTransaction("Bob");
        Transaction t2 = buildTransaction("Sam");

        Ship ship = new Ship("Ship", 100);
        ship.setCurrentUser(new User("Admin", "admin", "pw", Roles.QUARTERMASTER));
        ship.getTransactions().add(t1);
        ship.getTransactions().add(t2);
        ship.getCrew().add(t1.getCrewmember());
        ship.getCrew().add(t2.getCrewmember());

        StockChangesVM vm = new StockChangesVM(ship);
        
        assertEquals(ship, vm.getShip());
        assertEquals(2, vm.filteredTransactionsProperty().size());
        assertEquals(2, vm.filteredTransactionsProperty().get().size());

        assertEquals(SpecialQualities.values().length, vm.specialQualitiesListProperty().size());

        assertTrue(vm.selectedSpecialQualitiesProperty().isEmpty());

        assertEquals(2, vm.crewmemberListProperty().size());
        assertTrue(vm.crewmemberListProperty().contains("Bob"));
        assertTrue(vm.crewmemberListProperty().contains("Sam"));

        assertNull(vm.selectedCrewmemberProperty().get());
        assertNull(vm.startDateProperty().get());
        assertNull(vm.endDateProperty().get());
    }
}
