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
        int numContainersFromLoad = vm.getShip().getContainers().size();
        
        String result = vm.addContainer();

        assertTrue(result.startsWith("Container ID:"));
        assertEquals(numContainersFromLoad + 1, vm.getShip().getContainers().size());
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
    
    @Test
    void testAddContainerFailsDueToCapacity() {
		User currentUser = new User("Jack Sparrow", "jsparrow", "blackpearl", Roles.QUARTERMASTER);
		AddContainerVM vm = new AddContainerVM(currentUser);
		vm.getSizeProperty().set(Integer.MAX_VALUE);
		vm.getStockTypeProperty().set(StockType.FOOD);
		int sizeBefore = vm.getShip().getContainers().size();
		
		vm.addContainer();
		
		int result = vm.getShip().getContainers().size();

		assertEquals(sizeBefore, result);
	}
}
