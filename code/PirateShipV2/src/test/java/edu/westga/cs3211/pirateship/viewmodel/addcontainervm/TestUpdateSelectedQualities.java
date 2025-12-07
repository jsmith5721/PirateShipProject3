package edu.westga.cs3211.pirateship.viewmodel.addcontainervm;

import edu.westga.cs3211.pirateship.viewmodel.AddContainerVM;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUpdateSelectedQualities {

    @Test
    void testUpdateSelectedQualitiesUpdatesList() {
        Ship ship = new Ship("Black Pearl", 100);
        AddContainerVM vm = new AddContainerVM(ship);

        vm.updateSelectedQualities(List.of(SpecialQualities.FRAGILE, SpecialQualities.LIQUID));

        assertEquals(2, vm.getSelectedSpecialQualitiesProperty().size());
    }
}
