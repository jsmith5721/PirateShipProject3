package edu.westga.cs3211.pirateship.model.container;

import edu.westga.cs3211.pirateship.model.Container;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

public class TestUpdateIdCounter {

    @Test
    void testUpdateIdCounterIncreasesNextId() {
        Collection<Integer> ids = new ArrayList<>();
        ids.add(5);
        ids.add(12);
        ids.add(3);

        Container.updateIdCounter(ids);
        Container container = new Container(10, new ArrayList<>());
        
        assertEquals(13, container.getContainerID());
    }
}
