package edu.westga.cs3211.pirateship.viewmodel.addstockvm;

import edu.westga.cs3211.pirateship.viewmodel.AddStockVM;
import javafx.collections.FXCollections;
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
	User currentUser;
	@BeforeEach
	public void setUp() {
		currentUser = new User("Jack Sparrow", "jsparrow", "blackpearl", Roles.QUARTERMASTER);
	}
	
	@Test
    public void testAddStockThrowsForNullName() {
        ArrayList<SpecialQualities> qualities = new ArrayList<>();
        Container container = new Container(100, qualities);

        AddStockVM vm = new AddStockVM(this.currentUser);
        vm.getShip().addContainer(container);
        vm.getNameProperty().set(null);
        vm.getSizeProperty().set(10);
        vm.getQuantityProperty().set(2);
        vm.getConditionProperty().set(Conditions.GOOD);
        vm.getSelectedContainerProperty().set(container);

        assertThrows(IllegalArgumentException.class, () -> 
        	vm.addStock()
        );
    }
	
	@Test
    public void testAddStockThrowsForBlankName() {
        ArrayList<SpecialQualities> qualities = new ArrayList<>();
        Container container = new Container(100, qualities);

        AddStockVM vm = new AddStockVM(this.currentUser);
        vm.getShip().addContainer(container);
        vm.getNameProperty().set("");
        vm.getSizeProperty().set(10);
        vm.getQuantityProperty().set(2);
        vm.getConditionProperty().set(Conditions.GOOD);
        vm.getSelectedContainerProperty().set(container);

        assertThrows(IllegalArgumentException.class, () -> 
        	vm.addStock()
        );
    }

    @Test
    public void testAddStockThrowsForInvalidSize() {
        ArrayList<SpecialQualities> qualities = new ArrayList<>();
        Container container = new Container(100, qualities);

        AddStockVM vm = new AddStockVM(this.currentUser);
        vm.getShip().addContainer(container);
        vm.getNameProperty().set("Rope");
        vm.getSizeProperty().set(0);
        vm.getQuantityProperty().set(1);
        vm.getConditionProperty().set(Conditions.GOOD);
        vm.getSelectedContainerProperty().set(container);

        assertThrows(IllegalArgumentException.class, () ->
        	vm.addStock()
        );
    }

    @Test
    public void testAddStockThrowsForInvalidQuantity() {
        ArrayList<SpecialQualities> qualities = new ArrayList<>();
        Container container = new Container(100, qualities);

        AddStockVM vm = new AddStockVM(this.currentUser);
        vm.getShip().addContainer(container);
        vm.getNameProperty().set("Rope");
        vm.getSizeProperty().set(5);
        vm.getQuantityProperty().set(0);
        vm.getConditionProperty().set(Conditions.GOOD);
        vm.getSelectedContainerProperty().set(container);

        assertThrows(IllegalArgumentException.class, () -> vm.addStock());
    }

    @Test
    public void testAddStockThrowsForNullCondition() {
        ArrayList<SpecialQualities> qualities = new ArrayList<>();
        Container container = new Container(100, qualities);

        AddStockVM vm = new AddStockVM(this.currentUser);
        vm.getShip().addContainer(container);
        vm.getNameProperty().set("Rope");
        vm.getSizeProperty().set(5);
        vm.getQuantityProperty().set(1);
        vm.getSelectedContainerProperty().set(container);

        assertThrows(IllegalArgumentException.class, () -> vm.addStock());
    }

    @Test
    public void testAddStockThrowsForNoSelectedContainer() {
        AddStockVM vm = new AddStockVM(this.currentUser);

        vm.getNameProperty().set("Rope");
        vm.getSizeProperty().set(5);
        vm.getQuantityProperty().set(1);
        vm.getConditionProperty().set(Conditions.GOOD);

        assertThrows(IllegalArgumentException.class, () -> vm.addStock());
    }

    @Test
    public void testAddStockThrowsForMissingExpirationDate() {
        ArrayList<SpecialQualities> qualities = new ArrayList<>();
        qualities.add(SpecialQualities.PARISHABLE);

        Container container = new Container(100, qualities);

        AddStockVM vm = new AddStockVM(this.currentUser);
        vm.getShip().addContainer(container);
        vm.getNameProperty().set("Milk");
        vm.getSizeProperty().set(2);
        vm.getQuantityProperty().set(1);
        vm.getConditionProperty().set(Conditions.GOOD);
        vm.updateSelectedQualities(qualities);
        vm.getSelectedContainerProperty().set(container);

        assertThrows(IllegalArgumentException.class, () -> vm.addStock());
    }
    
    @Test
    public void testAddStockNoSpecialQualities() {
    	Container container = new Container(100, new ArrayList<>());

        AddStockVM vm = new AddStockVM(this.currentUser);
        vm.getShip().addContainer(container);
        vm.getNameProperty().set("Crates");
        vm.getSizeProperty().set(2);
        vm.getQuantityProperty().set(3);
        vm.getConditionProperty().set(Conditions.GOOD);
        vm.getSelectedContainerProperty().set(container);

        String result = vm.addStock();
        assertEquals("Stock \"Crates\" added to Container " + container.getContainerID() + ".", result);
    }
    
    @Test
    public void testAddStockNonPerishableWithQualities() {
    	Container container = new Container(100, new ArrayList<>());

        AddStockVM vm = new AddStockVM(this.currentUser);
        vm.getShip().addContainer(container);
        vm.getNameProperty().set("Swords");
        vm.getSizeProperty().set(1);
        vm.getQuantityProperty().set(10);
        vm.getConditionProperty().set(Conditions.NEW);
        vm.getSelectedContainerProperty().set(container);

        ArrayList<SpecialQualities> qualities = new ArrayList<>();
        qualities.add(SpecialQualities.VALUABLE);
        vm.getSelectedSpecialQualitiesProperty().set(FXCollections.observableArrayList(qualities));

        String result = vm.addStock();
        assertEquals("Stock \"Swords\" added to Container " + container.getContainerID() + ".", result);
    }
    
    @Test
    public void testAddStockPerishableMissingExpiration() {
    	Container container = new Container(100, new ArrayList<>());

        AddStockVM vm = new AddStockVM(this.currentUser);
        vm.getShip().addContainer(container);
        vm.getNameProperty().set("Food");
        vm.getSizeProperty().set(1);
        vm.getQuantityProperty().set(5);
        vm.getConditionProperty().set(Conditions.GOOD);
        vm.getSelectedContainerProperty().set(container);

        ArrayList<SpecialQualities> qualities = new ArrayList<>();
        qualities.add(SpecialQualities.PARISHABLE);
        vm.getSelectedSpecialQualitiesProperty().set(FXCollections.observableArrayList(qualities));

        assertThrows(IllegalArgumentException.class, () -> vm.addStock());
    }

    @Test
    public void testAddStockPerishableValid() {
    	Container container = new Container(100, new ArrayList<>());

        AddStockVM vm = new AddStockVM(this.currentUser);
        vm.getShip().addContainer(container);
        vm.getNameProperty().set("Meat");
        vm.getSizeProperty().set(2);
        vm.getQuantityProperty().set(4);
        vm.getConditionProperty().set(Conditions.GOOD);
        vm.getSelectedContainerProperty().set(container);

        ArrayList<SpecialQualities> qualities = new ArrayList<>();
        qualities.add(SpecialQualities.PARISHABLE);
        vm.getSelectedSpecialQualitiesProperty().set(FXCollections.observableArrayList(qualities));

        vm.getExpirationDateProperty().set(LocalDate.now().plusDays(2));

        String result = vm.addStock();
        assertEquals("Stock \"Meat\" added to Container " + container.getContainerID() + ".", result);
    }
    
    @Test
    public void testAddStockSuccessNonPerishable() {
        ArrayList<SpecialQualities> qualities = new ArrayList<>();
        Container container = new Container(100, qualities);

        AddStockVM vm = new AddStockVM(this.currentUser);
        vm.getShip().addContainer(container);
        vm.getNameProperty().set("Rope");
        vm.getSizeProperty().set(5);
        vm.getQuantityProperty().set(2);
        vm.getConditionProperty().set(Conditions.GOOD);
        vm.getSelectedContainerProperty().set(container);

        String result = vm.addStock();

        assertTrue(result.contains("Stock \"Rope\" added to Container"));
    }

    @Test
    public void testAddStockSuccessWithSpecialQualities() {
        ArrayList<SpecialQualities> qualities = new ArrayList<>();
        qualities.add(SpecialQualities.FRAGILE);
        qualities.add(SpecialQualities.VALUABLE);
        Container container = new Container(200, qualities);

        AddStockVM vm = new AddStockVM(this.currentUser);
        vm.getShip().addContainer(container);
        vm.getNameProperty().set("Lantern");
        vm.getSizeProperty().set(3);
        vm.getQuantityProperty().set(1);
        vm.getConditionProperty().set(Conditions.GOOD);
        vm.updateSelectedQualities(qualities);
        vm.getSelectedContainerProperty().set(container);
        
        String result = vm.addStock();

        assertTrue(result.contains("Lantern"));
    }

    @Test
    public void testAddStockSuccessPerishable() {
        ArrayList<SpecialQualities> qualities = new ArrayList<>();
        qualities.add(SpecialQualities.PARISHABLE);
        Container container = new Container(200, qualities);

        AddStockVM vm = new AddStockVM(this.currentUser);
        vm.getShip().addContainer(container);
        vm.getNameProperty().set("Fruit");
        vm.getSizeProperty().set(2);
        vm.getQuantityProperty().set(3);
        vm.getConditionProperty().set(Conditions.GOOD);
        vm.updateSelectedQualities(qualities);
        vm.getSelectedContainerProperty().set(container);
        vm.getExpirationDateProperty().set(LocalDate.now().plusDays(1));

        String result = vm.addStock();

        assertTrue(result.contains("Fruit"));
    }
}
