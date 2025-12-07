package edu.westga.cs3211.pirateship.viewmodel.addstockvm;

import edu.westga.cs3211.pirateship.viewmodel.AddStockVM;
import edu.westga.cs3211.pirateship.model.Ship;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestConstructor {

    @Test
    void testConstructorInitializesPropertiesCorrectly() {
        Ship ship = new Ship("Black Pearl", 300);
        AddStockVM vm = new AddStockVM(ship);

        assertEquals(ship, vm.getShip());
        assertEquals(5, vm.getSpecialQualitiesListProperty().size());
        assertEquals(0, vm.getSelectedSpecialQualitiesProperty().size());
        assertEquals(0, vm.getContainerListProperty().size());
    }
}
