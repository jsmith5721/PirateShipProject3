package edu.westga.cs3211.pirateship.model.serializer.transactionserializer;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.serializers.TransactionSerializer;

public class TestConstructor {
	@Test
	public void testConstructor() {
		TransactionSerializer serializer = new TransactionSerializer();
		assertNotNull(serializer);
	}
}
