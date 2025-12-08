package edu.westga.cs3211.pirateship.model.authenticator;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.Authenticator;

public class TestConstructor {
	@Test
	public void javaDefaultConstructor() {
		Authenticator authenticator = new Authenticator();
		assertNotNull(authenticator);
	}
}
