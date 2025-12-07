package edu.westga.cs3211.pirateship.viewmodel.addstockvm;

import edu.westga.cs3211.pirateship.viewmodel.AddStockVM;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.Roles;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestUpdateSelectedQualities {
	@Test
    void testSelectedNull() {
        Ship ship = new Ship("Test", 100);
        ship.setCurrentUser(new User("A", "a", "p", Roles.CREWMATE));

        AddStockVM vm = new AddStockVM(ship);

        assertThrows(NullPointerException.class, () -> 
        	vm.updateSelectedQualities(null)
        );
    }

    @Test
    void testSelectedEmpty() {
        Ship ship = new Ship("Test", 100);
        ship.setCurrentUser(new User("A", "a", "p", Roles.CREWMATE));

        AddStockVM vm = new AddStockVM(ship);
        vm.updateSelectedQualities(new ArrayList<>());

        assertEquals(0, vm.getContainerListProperty().size());
    }

    @Test
    void testNoMatchInContainers() {
        Ship ship = new Ship("Test", 100);
        ship.setCurrentUser(new User("A", "a", "p", Roles.CREWMATE));

        AddStockVM vm = new AddStockVM(ship);

        ArrayList<SpecialQualities> q = new ArrayList<>();
        q.add(SpecialQualities.EXPLOSIVE);

        vm.updateSelectedQualities(q);

        assertEquals(0, vm.getContainerListProperty().size());
    }
	
    @Test
    void testUpdateSelectedQualitiesFiltersContainersCorrectly() {
        Ship ship = new Ship("Black Pearl", 500);
        ArrayList<SpecialQualities> q1 = new ArrayList<>();
        q1.add(SpecialQualities.FRAGILE);

        ArrayList<SpecialQualities> q2 = new ArrayList<>();
        q2.add(SpecialQualities.EXPLOSIVE);
        q2.add(SpecialQualities.FRAGILE);

        Container c1 = new Container(100, q1);
        Container c2 = new Container(100, q2);
        ship.addContainer(c1);
        ship.addContainer(c2);

        AddStockVM vm = new AddStockVM(ship);
        vm.updateSelectedQualities(List.of(SpecialQualities.FRAGILE));

        assertEquals(2, vm.getContainerListProperty().size());
    }
}
