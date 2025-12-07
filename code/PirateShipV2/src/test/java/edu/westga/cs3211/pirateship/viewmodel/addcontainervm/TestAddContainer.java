package edu.westga.cs3211.pirateship.viewmodel.addcontainervm;

import edu.westga.cs3211.pirateship.viewmodel.AddContainerVM;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

public class TestAddContainer {

    @Test
    void testAddContainerSuccess() {
        Ship ship = new Ship("Black Pearl", 100);
        AddContainerVM vm = new AddContainerVM(ship);
        vm.getSizeProperty().set(20);
        vm.updateSelectedQualities(List.of(SpecialQualities.EXPLOSIVE));

        String result = vm.addContainer();

        assertTrue(result.startsWith("Container ID:"));
        assertEquals(1, ship.getContainers().size());
    }

    @Test
    void testAddContainerFailsReturnsEmptyString() {
        Ship ship = new Ship("Black Pearl", 10);
        AddContainerVM vm = new AddContainerVM(ship);
        vm.getSizeProperty().set(50);

        String result = vm.addContainer();

        assertEquals("", result);
    }
}
