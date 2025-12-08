package edu.westga.cs3211.pirateship.viewmodel.addstockvm;

import edu.westga.cs3211.pirateship.viewmodel.AddStockVM;
import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.User;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestConstructor {

    @Test
    void testConstructorInitializesPropertiesCorrectly() {
        User currentUser = new User("Jack Sparrow", "jsparrow", "blackpearl", Roles.QUARTERMASTER);
        AddStockVM vm = new AddStockVM(currentUser);

        assertEquals(currentUser, vm.getShip().getCurrentUser());
        assertEquals(5, vm.getSpecialQualitiesListProperty().size());
        assertEquals(0, vm.getSelectedSpecialQualitiesProperty().size());
        assertEquals(vm.getShip().getContainers().size(), vm.getContainerListProperty().size());
    }
}
