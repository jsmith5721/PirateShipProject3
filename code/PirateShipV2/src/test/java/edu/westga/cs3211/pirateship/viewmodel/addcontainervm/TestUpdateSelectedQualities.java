package edu.westga.cs3211.pirateship.viewmodel.addcontainervm;

import edu.westga.cs3211.pirateship.viewmodel.AddContainerVM;
import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.User;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUpdateSelectedQualities {

    @Test
    void testUpdateSelectedQualitiesUpdatesList() {
        Ship ship = new Ship("Black Pearl", 100);
        User currentUser = new User("Jack Sparrow", "jsparrow", "blackpearl", Roles.QUARTERMASTER);
        ship.setCurrentUser(currentUser);
        AddContainerVM vm = new AddContainerVM(ship.getCurrentUser());

        vm.updateSelectedQualities(List.of(SpecialQualities.FRAGILE, SpecialQualities.LIQUID));

        assertEquals(2, vm.getSelectedSpecialQualitiesProperty().size());
    }
}
