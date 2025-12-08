package edu.westga.cs3211.pirateship.viewmodel.landingpagevm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.viewmodel.LandingPageVM;

public class TestConstructor {
	private User quartermaster;
	private User crewmate;
	@BeforeEach
	void setUp() {
		quartermaster = new User("Jack Sparrow", "jsparrow", "blackpearl", Roles.QUARTERMASTER);
		crewmate = new User("Will Turner", "wturner", "blacksmith", Roles.CREWMATE);
	}
	
	@Test
    void testConstructorInitializesCorrectlyQuarterMaster() {
        LandingPageVM vm = new LandingPageVM(quartermaster);

        assertEquals(quartermaster, vm.getShip().getCurrentUser());
		assertEquals("Welcome, Jack Sparrow", vm.welcomeMessageProperty().get());
		assertTrue(vm.canReviewStockChangesProperty().get());
    }
    
	@Test
	void testConstructorInitializesCorrectlyCrewmate() {
		LandingPageVM vm = new LandingPageVM(crewmate);

		assertEquals(crewmate, vm.getShip().getCurrentUser());
		assertEquals("Welcome, Will Turner", vm.welcomeMessageProperty().get());
		assertFalse(vm.canReviewStockChangesProperty().get());
	}
}
