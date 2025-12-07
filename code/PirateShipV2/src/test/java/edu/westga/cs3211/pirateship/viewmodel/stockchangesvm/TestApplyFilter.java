package edu.westga.cs3211.pirateship.viewmodel.stockchangesvm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.Transaction;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.viewmodel.StockChangesVM;

public class TestApplyFilter {

    private Ship ship;
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
        this.ship = new Ship("Ship", 100);
        this.userA = new User("Alice", "alice", "pw", Roles.CREWMATE);
        this.userB = new User("Bob", "bob", "pw", Roles.CREWMATE);

        this.ship.getCrew().add(this.userA);
        this.ship.getCrew().add(this.userB);

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

        this.ship.getTransactions().add(this.t1);
        this.ship.getTransactions().add(this.t2);
        this.ship.getTransactions().add(this.t3);
    }

    @Test
    void testApplyFilterNoFiltersShowsAll() {
        StockChangesVM vm = new StockChangesVM(this.ship);
        vm.applyFilter();
        assertEquals(3, vm.filteredTransactionsProperty().size());
    }

    @Test
    void testApplyFilterStartDateOnly() {
        StockChangesVM vm = new StockChangesVM(this.ship);
        vm.startDateProperty().set(LocalDate.of(2024, 2, 1));
        vm.applyFilter();
        assertEquals(2, vm.filteredTransactionsProperty().size());
    }

    @Test
    void testApplyFilterEndDateOnly() {
        StockChangesVM vm = new StockChangesVM(this.ship);
        vm.endDateProperty().set(LocalDate.of(2024, 2, 20));
        vm.applyFilter();
        assertEquals(2, vm.filteredTransactionsProperty().size());
    }

    @Test
    void testApplyFilterDateRange() {
        StockChangesVM vm = new StockChangesVM(this.ship);
        vm.startDateProperty().set(LocalDate.of(2024, 2, 1));
        vm.endDateProperty().set(LocalDate.of(2024, 3, 1));
        vm.applyFilter();
        assertEquals(1, vm.filteredTransactionsProperty().size());
    }

    @Test
    void testApplyFilterSpecialQualitiesSingleMatch() {
        StockChangesVM vm = new StockChangesVM(this.ship);
        vm.selectedSpecialQualitiesProperty().add(SpecialQualities.LIQUID);
        vm.applyFilter();
        assertEquals(1, vm.filteredTransactionsProperty().size());
    }

    @Test
    void testApplyFilterSpecialQualitiesMultipleRequired() {
        StockChangesVM vm = new StockChangesVM(this.ship);
        vm.selectedSpecialQualitiesProperty().add(SpecialQualities.FRAGILE);
        vm.selectedSpecialQualitiesProperty().add(SpecialQualities.EXPLOSIVE);
        vm.applyFilter();
        assertEquals(1, vm.filteredTransactionsProperty().size());
    }

    @Test
    void testApplyFilterCrewMemberOnly() {
        StockChangesVM vm = new StockChangesVM(this.ship);
        vm.selectedCrewmemberProperty().set("Alice");
        vm.applyFilter();
        assertEquals(2, vm.filteredTransactionsProperty().size());
    }

    @Test
    void testApplyFilterAllFiltersCombined() {
        StockChangesVM vm = new StockChangesVM(this.ship);

        vm.startDateProperty().set(LocalDate.of(2024, 3, 1));
        vm.endDateProperty().set(LocalDate.of(2024, 3, 20));

        vm.selectedCrewmemberProperty().set("Alice");

        vm.selectedSpecialQualitiesProperty().add(SpecialQualities.EXPLOSIVE);

        vm.applyFilter();

        assertEquals(1, vm.filteredTransactionsProperty().size());
        assertEquals(this.t3, vm.filteredTransactionsProperty().get(0));
    }
}
