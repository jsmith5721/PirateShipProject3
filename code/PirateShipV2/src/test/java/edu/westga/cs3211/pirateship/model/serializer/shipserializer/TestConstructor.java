package edu.westga.cs3211.pirateship.model.serializer.shipserializer;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.serializers.ShipSerializer;

public class TestConstructor {
	@Test
	public void testDefaultConstructor() {
		ShipSerializer serializer = new ShipSerializer();
		assertNotNull(serializer);
	}
}
