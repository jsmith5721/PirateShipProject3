package edu.westga.cs3211.pirateship.viewmodel.stockchangesvm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.Transaction;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.viewmodel.StockChangesVM;

public class TestApplyFilter {
	private StockChangesVM vm;
	private User currentUser;
    private User userA;
    private User userB;
    private Transaction t1;
    private Transaction t2;
    private Transaction t3;

    private Date toDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @BeforeEach
    void setup() {
        this.currentUser = new User("Jack Sparrow", "jsparrow", "blackpearl", Roles.QUARTERMASTER);
        this.vm = new StockChangesVM(this.currentUser);
        
        this.userA = new User("Alice", "alice", "pw", Roles.CREWMATE);
        this.userB = new User("Bob", "bob", "pw", Roles.CREWMATE);
        this.vm.getShip().getCrew().add(this.userA);
        this.vm.getShip().getCrew().add(this.userB);
        this.vm.crewmemberListProperty().add(this.userA.getName());
        this.vm.crewmemberListProperty().add(this.userB.getName());

        ArrayList<SpecialQualities> q1 = new ArrayList<>();
        q1.add(SpecialQualities.FRAGILE);

        ArrayList<SpecialQualities> q2 = new ArrayList<>();
        q2.add(SpecialQualities.LIQUID);

        ArrayList<SpecialQualities> q3 = new ArrayList<>();
        q3.add(SpecialQualities.FRAGILE);
        q3.add(SpecialQualities.EXPLOSIVE);

        this.t1 = new Transaction(toDate(LocalDate.of(2024, 1, 10)), "A", 1, this.userA, q1);
        this.t2 = new Transaction(toDate(LocalDate.of(2024, 2, 15)), "B", 1, this.userB, q2);
        this.t3 = new Transaction(toDate(LocalDate.of(2024, 3, 10)), "C", 1, this.userA, q3);

        this.vm.getShip().getTransactions().add(this.t1);
        this.vm.getShip().getTransactions().add(this.t2);
        this.vm.getShip().getTransactions().add(this.t3);
        this.vm.masterTransactionListProperty().addAll(this.t1, this.t2, this.t3);
    }
    
    @Test
    public void testInitialFilteredListMatchesMasterList() {
    	this.vm.applyFilter();
		assertEquals(this.vm.masterTransactionListProperty().size(), this.vm.filteredTransactionsProperty().size());
	}
    
    @Test
    public void testNoFilterForCrewmemberNull() {
		this.vm.selectedCrewmemberProperty().set(null);
		this.vm.applyFilter();
		assertEquals(this.vm.masterTransactionListProperty().size(), this.vm.filteredTransactionsProperty().size());
    }
    
    @Test
    public void testNoFilterForCrewmemberBlank() {
		this.vm.selectedCrewmemberProperty().set("");
		this.vm.applyFilter();
		assertEquals(this.vm.masterTransactionListProperty().size(), this.vm.filteredTransactionsProperty().size());
    }

    @Test
    void testApplyFilterNoFiltersShowsAll() {
        vm.applyFilter();
        assertEquals(3, vm.filteredTransactionsProperty().size());
    }

    @Test
    void testApplyFilterStartDateOnly() {
        vm.startDateProperty().set(LocalDate.of(2024, 2, 1));
        vm.applyFilter();
        assertEquals(2, vm.filteredTransactionsProperty().size());
    }

    @Test
    void testApplyFilterEndDateOnly() {
        vm.endDateProperty().set(LocalDate.of(2024, 2, 20));
        vm.applyFilter();
        assertEquals(2, vm.filteredTransactionsProperty().size());
    }

    @Test
    void testApplyFilterDateRange() {
        vm.startDateProperty().set(LocalDate.of(2024, 2, 1));
        vm.endDateProperty().set(LocalDate.of(2024, 3, 1));
        vm.applyFilter();
        assertEquals(1, vm.filteredTransactionsProperty().size());
    }

    @Test
    void testApplyFilterSpecialQualitiesSingleMatch() {
        vm.selectedSpecialQualitiesProperty().add(SpecialQualities.LIQUID);
        vm.applyFilter();
        assertEquals(1, vm.filteredTransactionsProperty().size());
    }

    @Test
    void testApplyFilterSpecialQualitiesMultipleRequired() {
        vm.selectedSpecialQualitiesProperty().add(SpecialQualities.FRAGILE);
        vm.selectedSpecialQualitiesProperty().add(SpecialQualities.EXPLOSIVE);
        vm.applyFilter();
        assertEquals(1, vm.filteredTransactionsProperty().size());
    }

    @Test
    void testApplyFilterCrewMemberOnly() {
        vm.selectedCrewmemberProperty().set("Alice");
        vm.applyFilter();
        assertEquals(2, vm.filteredTransactionsProperty().size());
    }

    @Test
    void testApplyFilterAllFiltersCombined() {
    	
        vm.startDateProperty().set(LocalDate.of(2024, 3, 1));
        vm.endDateProperty().set(LocalDate.of(2024, 3, 20));

        vm.selectedCrewmemberProperty().set("Alice");

        vm.selectedSpecialQualitiesProperty().add(SpecialQualities.EXPLOSIVE);

        vm.applyFilter();

        assertEquals(1, vm.filteredTransactionsProperty().size());
        assertEquals(this.t3, vm.filteredTransactionsProperty().get(0));
    }
}
