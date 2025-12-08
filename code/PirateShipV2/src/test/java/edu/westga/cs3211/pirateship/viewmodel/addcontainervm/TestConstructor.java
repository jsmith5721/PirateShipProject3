package edu.westga.cs3211.pirateship.viewmodel.addcontainervm;

import edu.westga.cs3211.pirateship.viewmodel.AddContainerVM;
import edu.westga.cs3211.pirateship.model.Roles;

import edu.westga.cs3211.pirateship.model.User;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestConstructor {

    @Test
    void testConstructorInitializesLists() {
        User currentUser = new User("Jack Sparrow", "jsparrow", "blackpearl", Roles.QUARTERMASTER);
        AddContainerVM vm = new AddContainerVM(currentUser);
        vm.getShip().setCurrentUser(currentUser);

        assertEquals(currentUser, vm.getShip().getCurrentUser());
        assertEquals(5, vm.getSpecialQualityProperty().size());
        assertEquals(0, vm.getSelectedSpecialQualitiesProperty().size());
    }
}
