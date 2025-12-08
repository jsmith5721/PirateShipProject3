package edu.westga.cs3211.pirateship.viewmodel.addstockvm;

import edu.westga.cs3211.pirateship.viewmodel.AddStockVM;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.Roles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUpdateSelectedQualities {
	User currentUser;
	
	@BeforeEach
	void setUp() {
		currentUser = new User("A", "a", "p", Roles.CREWMATE);
	}
	
	@Test
    void testSelectedNull() {
        AddStockVM vm = new AddStockVM(this.currentUser);

        assertThrows(NullPointerException.class, () -> 
        	vm.updateSelectedQualities(null)
        );
    }

    @Test
    void testSelectedEmpty() {
        AddStockVM vm = new AddStockVM(currentUser);
        vm.updateSelectedQualities(new ArrayList<>());

        assertEquals(0, vm.getContainerListProperty().size());
        assertFalse(vm.showExpirationProperty().get());
    }

    @Test
    void testNoMatchInContainers() {
        AddStockVM vm = new AddStockVM(currentUser);

        ArrayList<SpecialQualities> qualities = new ArrayList<>();
        qualities.add(SpecialQualities.PARISHABLE);

        vm.updateSelectedQualities(qualities);

        assertEquals(0, vm.getContainerListProperty().size());
        assertTrue(vm.showExpirationProperty().get());
    }
	
    @Test
    void testUpdateSelectedQualitiesFiltersContainersCorrectly() {
        ArrayList<SpecialQualities> q1 = new ArrayList<>();
        q1.add(SpecialQualities.FRAGILE);

        ArrayList<SpecialQualities> q2 = new ArrayList<>();
        q2.add(SpecialQualities.EXPLOSIVE);
        q2.add(SpecialQualities.FRAGILE);

        Container c1 = new Container(100, q1);
        Container c2 = new Container(100, q2);

        AddStockVM vm = new AddStockVM(currentUser);
        vm.getShip().addContainer(c1);
        vm.getShip().addContainer(c2);
        vm.updateSelectedQualities(List.of(SpecialQualities.FRAGILE));

        assertEquals(2, vm.getContainerListProperty().size());
        assertFalse(vm.showExpirationProperty().get());
    }
}
