package edu.westga.cs3211.pirateship.model.serializer.transactionserializer;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.StockType;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.model.serializers.TransactionSerializer;
import edu.westga.cs3211.pirateship.model.Transaction;

public class TestSaveTransactionHistory {
	Ship ship;
	User user;
	Container container1;
	Container container2;
	Container container3;
	User crewMember1;
	User crewMember2;
	Transaction transaction1;
	Transaction transaction2;
	Transaction transaction3;
	Transaction transaction4;
	
	private Date toDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
	
	@BeforeEach
	public void setUp() {
		this.ship = new Ship("Black Pearl", 10000);
		this.user = new User("Jack Sparrow", "password", "Captain", Roles.QUARTERMASTER);
		this.ship.setCurrentUser(this.user);
		
		List<SpecialQualities> qualitiesParish = new ArrayList<SpecialQualities>();
		qualitiesParish.add(SpecialQualities.PARISHABLE);
		
		List<SpecialQualities> qualitiesFragAndExplo = new ArrayList<SpecialQualities>();
		qualitiesFragAndExplo.add(SpecialQualities.FRAGILE);
		qualitiesFragAndExplo.add(SpecialQualities.EXPLOSIVE);
		
		List<SpecialQualities> qualitiesValuable = new ArrayList<SpecialQualities>();
		qualitiesValuable.add(SpecialQualities.VALUABLE);
		
		crewMember1 = new User("Will Turner", "willt", "swordfish", Roles.CREWMATE);
		crewMember2 = new User("Elizabeth Swann", "lizs", "piratequeen", Roles.QUARTERMASTER);
		
		transaction1 = new Transaction(toDate(LocalDate.of(2024, 1, 10)), "Fruit", 100, crewMember1, StockType.FOOD, qualitiesParish);
		transaction2 = new Transaction(toDate(LocalDate.of(2024, 2, 15)), "Ammo", 50, crewMember2, StockType.AMMUNITION, qualitiesFragAndExplo);
		transaction3 = new Transaction(toDate(LocalDate.of(2024, 3, 10)), "Gold Coins", 200, crewMember1, StockType.OTHER, qualitiesValuable);
		transaction4 = new Transaction(toDate(LocalDate.of(2024, 4, 5)), "Gems", 50, crewMember2, StockType.OTHER, qualitiesValuable);
	}
	
	@Test
	public void testSaveTransactionHistoryNoTransactions() {
		TransactionSerializer.saveTransactionHistory(ship, "TestTransactions.txt");
		List<Transaction> loadedTransactions = TransactionSerializer.loadTransactionHistory("TestTransactions.txt");
		
		assertNull(loadedTransactions);
	}
	
	@Test
	public void testSaveTransactionHistoryWithTransactions() {
		ship.getTransactions().add(transaction1);
		ship.getTransactions().add(transaction2);
		ship.getTransactions().add(transaction3);
		ship.getTransactions().add(transaction4);
		
		TransactionSerializer.saveTransactionHistory(ship, "TestTransactions.txt");
		List<Transaction> loadedTransactions = TransactionSerializer.loadTransactionHistory("TestTransactions.txt");
		
		ArrayList<Date> loadedTransactionDates = new ArrayList<Date>();
		for (Transaction transaction : loadedTransactions) {
			loadedTransactionDates.add(transaction.getDate());
		}
		
		assertTrue(loadedTransactionDates.contains(transaction1.getDate()));
		assertTrue(loadedTransactionDates.contains(transaction1.getDate()));
		assertTrue(loadedTransactionDates.contains(transaction1.getDate()));
		assertTrue(loadedTransactionDates.contains(transaction1.getDate()));
	}
}