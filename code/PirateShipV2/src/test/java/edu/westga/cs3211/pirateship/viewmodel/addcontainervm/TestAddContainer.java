package edu.westga.cs3211.pirateship.viewmodel.addcontainervm;

import edu.westga.cs3211.pirateship.viewmodel.AddContainerVM;
import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.StockType;
import edu.westga.cs3211.pirateship.model.User;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

public class TestAddContainer {

    @Test
    void testAddContainerSuccess() {
        User currentUser = new User("Jack Sparrow", "jsparrow", "blackpearl", Roles.QUARTERMASTER);
        AddContainerVM vm = new AddContainerVM(currentUser);
        vm.getSizeProperty().set(20);
        vm.getStockTypeProperty().set(StockType.AMMUNITION);
        vm.updateSelectedQualities(List.of(SpecialQualities.EXPLOSIVE));

        String result = vm.addContainer();

        assertTrue(result.startsWith("Container ID:"));
        assertEquals(1, vm.getShip().getContainers().size());
    }

    @Test
    void testAddContainerFailsThrowsException() {
        User currentUser = new User("Jack Sparrow", "jsparrow", "blackpearl", Roles.QUARTERMASTER);
        AddContainerVM vm = new AddContainerVM(currentUser);
        vm.getSizeProperty().set(0);

        assertThrows(IllegalArgumentException.class, () -> 
        	vm.addContainer()
        );
    }
}
