package edu.westga.cs3211.pirateship.viewmodel.addstockvm;

import edu.westga.cs3211.pirateship.viewmodel.AddStockVM;
import edu.westga.cs3211.pirateship.model.Ship;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSaveData {

    private static class ShipDouble extends Ship {
        boolean saveCalled;

        public ShipDouble() {
            super("Test", 50);
        }

        @Override
        public void saveData() {
            this.saveCalled = true;
        }
    }

    @Test
    void testSaveDataCallsShip() {
        ShipDouble ship = new ShipDouble();
        AddStockVM vm = new AddStockVM(ship);

        vm.saveData();

        assertTrue(ship.saveCalled);
    }
}
