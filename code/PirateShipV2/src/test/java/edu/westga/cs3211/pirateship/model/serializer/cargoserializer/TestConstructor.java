package edu.westga.cs3211.pirateship.model.serializer.cargoserializer;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.serializers.CargoSerializer;

public class TestConstructor {
	@Test
	public void testConstructor() {
		CargoSerializer serializer = new CargoSerializer();	
		assertNotNull(serializer);
	}
}
