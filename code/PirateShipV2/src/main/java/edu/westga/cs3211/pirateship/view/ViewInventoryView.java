package edu.westga.cs3211.pirateship.view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import edu.westga.cs3211.pirateship.model.Stock;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.viewmodel.AddContainerVM;
import edu.westga.cs3211.pirateship.viewmodel.ViewInventoryVM;

import java.time.LocalDate;

public class ViewInventoryView {

	@FXML
	private TableView<Stock> stockTable;

	@FXML
	private TableColumn<Stock, String> nameCol;

	@FXML
	private TableColumn<Stock, Number> qtyCol;

	@FXML
	private TableColumn<Stock, String> conditionCol;

	@FXML
	private TableColumn<Stock, String> specialCol;

	@FXML
	private TableColumn<Stock, LocalDate> expCol;

	private ViewInventoryVM viewModel;


	public void setShip(Ship ship) {
		this.viewModel = new ViewInventoryVM(ship);
		this.bindViewModel();
	}

	private void bindViewModel() {
		nameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));

		qtyCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getQuantity()));

		conditionCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCondition().toString()));

		specialCol.setCellValueFactory(
				cell -> new SimpleStringProperty(cell.getValue().getSpecialQualities().toString()));

		expCol.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getExpirationDate()));

		Ship ship = getCurrentShipSomehow(); // see note below
		this.viewModel.loadEntireInventory(ship);

		stockTable.setItems(this.viewModel.getStocks());
	}

	/**
	 * TEMP placeholder – replace this with however your app gets the current Ship.
	 * Could be a singleton, passed from LandingPage, from Authenticator, etc.
	 */
	private Ship getCurrentShipSomehow() {
		return null;
	}
}
