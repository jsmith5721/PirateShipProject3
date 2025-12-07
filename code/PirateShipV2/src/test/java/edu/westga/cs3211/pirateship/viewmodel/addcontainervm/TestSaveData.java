package edu.westga.cs3211.pirateship.viewmodel.addcontainervm;

import edu.westga.cs3211.pirateship.viewmodel.AddContainerVM;
import edu.westga.cs3211.pirateship.model.Ship;
import org.junit.jupiter.api.Test;

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
    void testSaveDataCallsShipSave() {
        ShipDouble ship = new ShipDouble();
        AddContainerVM vm = new AddContainerVM(ship);

        vm.saveData();

        org.junit.jupiter.api.Assertions.assertTrue(ship.saveCalled);
    }
}
