package edu.westga.cs3211.pirateship.viewmodel.addstockvm;

import edu.westga.cs3211.pirateship.viewmodel.AddStockVM;
import javafx.collections.FXCollections;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.model.Conditions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestAddStock {
	Ship ship;
	User currentUser;
	@BeforeEach
	void setUp() {
		ship = new Ship("Black Pearl", 500);
		currentUser = new User("Jack Sparrow", "jsparrow", "blackpearl", Roles.QUARTERMASTER);
		ship.setCurrentUser(currentUser);
	}
	
	@Test
    void testAddStockThrowsForBlankName() {
        ArrayList<SpecialQualities> q = new ArrayList<>();
        Container c = new Container(100, q);
        ship.addContainer(c);

        AddStockVM vm = new AddStockVM(ship);
        vm.getSizeProperty().set(10);
        vm.getQuantityProperty().set(2);
        vm.getConditionProperty().set(Conditions.GOOD);
        vm.getSelectedContainerProperty().set(c);

        assertThrows(IllegalArgumentException.class, () -> vm.addStock());
    }

    @Test
    void testAddStockThrowsForInvalidSize() {
        ArrayList<SpecialQualities> q = new ArrayList<>();
        Container c = new Container(100, q);
        ship.addContainer(c);

        AddStockVM vm = new AddStockVM(ship);
        vm.getNameProperty().set("Rope");
        vm.getSizeProperty().set(0);
        vm.getQuantityProperty().set(1);
        vm.getConditionProperty().set(Conditions.GOOD);
        vm.getSelectedContainerProperty().set(c);

        assertThrows(IllegalArgumentException.class, () -> vm.addStock());
    }

    @Test
    void testAddStockThrowsForInvalidQuantity() {
        ArrayList<SpecialQualities> q = new ArrayList<>();
        Container c = new Container(100, q);
        ship.addContainer(c);

        AddStockVM vm = new AddStockVM(ship);
        vm.getNameProperty().set("Rope");
        vm.getSizeProperty().set(5);
        vm.getQuantityProperty().set(0);
        vm.getConditionProperty().set(Conditions.GOOD);
        vm.getSelectedContainerProperty().set(c);

        assertThrows(IllegalArgumentException.class, () -> vm.addStock());
    }

    @Test
    void testAddStockThrowsForNullCondition() {
        ArrayList<SpecialQualities> q = new ArrayList<>();
        Container c = new Container(100, q);
        ship.addContainer(c);

        AddStockVM vm = new AddStockVM(ship);
        vm.getNameProperty().set("Rope");
        vm.getSizeProperty().set(5);
        vm.getQuantityProperty().set(1);
        vm.getSelectedContainerProperty().set(c);

        assertThrows(IllegalArgumentException.class, () -> vm.addStock());
    }

    @Test
    void testAddStockThrowsForNoSelectedContainer() {
        AddStockVM vm = new AddStockVM(ship);

        vm.getNameProperty().set("Rope");
        vm.getSizeProperty().set(5);
        vm.getQuantityProperty().set(1);
        vm.getConditionProperty().set(Conditions.GOOD);

        assertThrows(IllegalArgumentException.class, () -> vm.addStock());
    }

    @Test
    void testAddStockThrowsForMissingExpirationDate() {
        ArrayList<SpecialQualities> q = new ArrayList<>();
        q.add(SpecialQualities.PARISHABLE);

        Container c = new Container(100, q);
        ship.addContainer(c);

        AddStockVM vm = new AddStockVM(ship);
        vm.getNameProperty().set("Milk");
        vm.getSizeProperty().set(2);
        vm.getQuantityProperty().set(1);
        vm.getConditionProperty().set(Conditions.GOOD);
        vm.updateSelectedQualities(q);
        vm.getSelectedContainerProperty().set(c);

        assertThrows(IllegalArgumentException.class, () -> vm.addStock());
    }
    
    @Test
    void testAddStockNoSpecialQualities() {
        ship.addContainer(new Container(50, new java.util.ArrayList<>()));

        AddStockVM vm = new AddStockVM(ship);
        vm.getNameProperty().set("Crates");
        vm.getSizeProperty().set(2);
        vm.getQuantityProperty().set(3);
        vm.getConditionProperty().set(Conditions.GOOD);
        vm.getSelectedContainerProperty().set(ship.getContainers().get(0));

        String result = vm.addStock();
        assertEquals("Stock \"Crates\" added to Container 1.", result);
    }
    
    @Test
    void testAddStockNonPerishableWithQualities() {
        ship.addContainer(new Container(50, new ArrayList<>()));

        AddStockVM vm = new AddStockVM(ship);
        vm.getNameProperty().set("Swords");
        vm.getSizeProperty().set(1);
        vm.getQuantityProperty().set(10);
        vm.getConditionProperty().set(Conditions.NEW);
        vm.getSelectedContainerProperty().set(ship.getContainers().get(0));

        ArrayList<SpecialQualities> q = new ArrayList<>();
        q.add(SpecialQualities.VALUABLE);
        vm.getSelectedSpecialQualitiesProperty().set(FXCollections.observableArrayList(q));

        String result = vm.addStock();
        assertEquals("Stock \"Swords\" added to Container 1.", result);
    }
    
    @Test
    void testAddStockPerishableMissingExpiration() {
        ship.addContainer(new Container(50, new ArrayList<>()));

        AddStockVM vm = new AddStockVM(ship);
        vm.getNameProperty().set("Food");
        vm.getSizeProperty().set(1);
        vm.getQuantityProperty().set(5);
        vm.getConditionProperty().set(Conditions.GOOD);
        vm.getSelectedContainerProperty().set(ship.getContainers().get(0));

        ArrayList<SpecialQualities> q = new ArrayList<>();
        q.add(SpecialQualities.PARISHABLE);
        vm.getSelectedSpecialQualitiesProperty().set(FXCollections.observableArrayList(q));

        assertThrows(IllegalArgumentException.class, () -> vm.addStock());
    }

    @Test
    void testAddStockPerishableValid() {
        ship.addContainer(new Container(50, new ArrayList<>()));

        AddStockVM vm = new AddStockVM(ship);
        vm.getNameProperty().set("Meat");
        vm.getSizeProperty().set(2);
        vm.getQuantityProperty().set(4);
        vm.getConditionProperty().set(Conditions.GOOD);
        vm.getSelectedContainerProperty().set(ship.getContainers().get(0));

        ArrayList<SpecialQualities> q = new ArrayList<>();
        q.add(SpecialQualities.PARISHABLE);
        vm.getSelectedSpecialQualitiesProperty().set(FXCollections.observableArrayList(q));

        vm.getExpirationDateProperty().set(LocalDate.now().plusDays(2));

        String result = vm.addStock();
        assertEquals("Stock \"Meat\" added to Container 1.", result);
    }
    
    @Test
    void testAddStockSuccessNonPerishable() {
        ArrayList<SpecialQualities> q = new ArrayList<>();
        Container c = new Container(100, q);
        ship.addContainer(c);

        AddStockVM vm = new AddStockVM(ship);
        vm.getNameProperty().set("Rope");
        vm.getSizeProperty().set(5);
        vm.getQuantityProperty().set(2);
        vm.getConditionProperty().set(Conditions.GOOD);
        vm.getSelectedContainerProperty().set(c);

        String result = vm.addStock();

        assertTrue(result.contains("Stock \"Rope\" added to Container"));
    }

    @Test
    void testAddStockSuccessWithSpecialQualities() {
        ArrayList<SpecialQualities> q = new ArrayList<>();
        q.add(SpecialQualities.FRAGILE);
        Container c = new Container(200, q);
        ship.addContainer(c);

        AddStockVM vm = new AddStockVM(ship);
        vm.getNameProperty().set("Lantern");
        vm.getSizeProperty().set(3);
        vm.getQuantityProperty().set(1);
        vm.getConditionProperty().set(Conditions.GOOD);
        vm.updateSelectedQualities(q);
        vm.getSelectedContainerProperty().set(c);

        String result = vm.addStock();

        assertTrue(result.contains("Lantern"));
    }

    @Test
    void testAddStockSuccessPerishable() {
        ArrayList<SpecialQualities> q = new ArrayList<>();
        q.add(SpecialQualities.PARISHABLE);
        Container c = new Container(200, q);
        ship.addContainer(c);

        AddStockVM vm = new AddStockVM(ship);
        vm.getNameProperty().set("Fruit");
        vm.getSizeProperty().set(2);
        vm.getQuantityProperty().set(3);
        vm.getConditionProperty().set(Conditions.GOOD);
        vm.updateSelectedQualities(q);
        vm.getSelectedContainerProperty().set(c);
        vm.getExpirationDateProperty().set(LocalDate.now().plusDays(1));

        String result = vm.addStock();

        assertTrue(result.contains("Fruit"));
    }
}
