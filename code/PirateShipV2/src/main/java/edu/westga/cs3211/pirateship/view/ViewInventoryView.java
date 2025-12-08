package edu.westga.cs3211.pirateship.view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import edu.westga.cs3211.pirateship.model.Stock;
import edu.westga.cs3211.pirateship.model.Ship;
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
	
	private Ship ship;

	/*
	 * Sets the given ship as the current ship for the inventory
	 */
	public void setShip(Ship ship) {
		this.ship = ship;
		this.viewModel = new ViewInventoryVM(ship);
		this.bindViewModel();
	}

	/*
	 * Sets the scene to show the landing page once more
	 */
	public void returnToLandingPage(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(LandingPage.class.getResource("LandingPage.fxml"));
			loader.load();
			LandingPage landingPageController = loader.getController();
			landingPageController.startup(this.ship.getCurrentUser());
			
			Parent root = loader.getRoot();
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.setTitle("Flying Dutchman - Home");
	        stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading Landing Page.");
            alert.showAndWait();
        }
	}
	
	private void bindViewModel() {
		this.nameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));

		this.qtyCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getQuantity()));

		this.conditionCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCondition().toString()));

		this.specialCol.setCellValueFactory(
				cell -> new SimpleStringProperty(cell.getValue().getSpecialQualities().toString()));

		this.expCol.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getExpirationDate()));

		this.viewModel.loadEntireInventory(this.ship);

		this.stockTable.setItems(this.viewModel.getStocks());
	}
}
