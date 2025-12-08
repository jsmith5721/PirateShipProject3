package edu.westga.cs3211.pirateship.viewmodel.stockchangesvm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.viewmodel.StockChangesVM;

public class TestConstructor {
    @Test
    void testConstructorInitializesAllFieldsCorrectly() {
        User currentUser = new User("Admin", "admin", "pw", Roles.QUARTERMASTER);

        StockChangesVM vm = new StockChangesVM(currentUser);
        vm.getShip().getTransactions().clear();
        vm.masterTransactionListProperty().setAll(vm.getShip().getTransactions());
        vm.filteredTransactionsProperty().setAll(vm.getShip().getTransactions());
        
        assertEquals(currentUser, vm.getShip().getCurrentUser());
        assertEquals(0, vm.filteredTransactionsProperty().size());
        assertEquals(0, vm.filteredTransactionsProperty().get().size());

        assertEquals(SpecialQualities.values().length, vm.specialQualitiesListProperty().size());

        assertTrue(vm.selectedSpecialQualitiesProperty().isEmpty());

        assertEquals(4, vm.crewmemberListProperty().size());

        assertNull(vm.selectedCrewmemberProperty().get());
        assertNull(vm.startDateProperty().get());
        assertNull(vm.endDateProperty().get());
        assertNull(vm.getStockTypeProperty().get());
    }
}
