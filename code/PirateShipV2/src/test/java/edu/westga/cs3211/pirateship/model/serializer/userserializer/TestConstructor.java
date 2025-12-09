package edu.westga.cs3211.pirateship.model.serializer.userserializer;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.serializers.UserSerializer;

public class TestConstructor {
	@Test
	public void testConstructor() {
		UserSerializer serializer = new UserSerializer();	
		assertNotNull(serializer);
	}
}
