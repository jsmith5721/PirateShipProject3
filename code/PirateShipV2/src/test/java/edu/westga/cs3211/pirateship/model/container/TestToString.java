package edu.westga.cs3211.pirateship.model.container;

import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;

public class TestToString {

    @Test
    void testToStringIncludesContainerIdAndCapacityAndQualities() {
        ArrayList<SpecialQualities> qualities = new ArrayList<>();
        qualities.add(SpecialQualities.FRAGILE);
        qualities.add(SpecialQualities.LIQUID);

        Container container = new Container(20, qualities);
        String text = container.toString();

        assertTrue(text.contains("Container ID"));
        assertTrue(text.contains("Remaining Capacity"));
        assertTrue(text.contains("FRAGILE"));
        assertTrue(text.contains("LIQUID"));
    }
}
