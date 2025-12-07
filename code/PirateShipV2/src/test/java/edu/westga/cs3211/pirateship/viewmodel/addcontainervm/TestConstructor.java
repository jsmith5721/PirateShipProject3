package edu.westga.cs3211.pirateship.viewmodel.addcontainervm;

import edu.westga.cs3211.pirateship.viewmodel.AddContainerVM;
import edu.westga.cs3211.pirateship.model.Ship;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestConstructor {

    @Test
    void testConstructorInitializesLists() {
        Ship ship = new Ship("Black Pearl", 100);
        AddContainerVM vm = new AddContainerVM(ship);

        assertEquals(ship, vm.getShip());
        assertEquals(5, vm.getSpecialQualityProperty().size());
        assertEquals(0, vm.getSelectedSpecialQualitiesProperty().size());
    }
}
