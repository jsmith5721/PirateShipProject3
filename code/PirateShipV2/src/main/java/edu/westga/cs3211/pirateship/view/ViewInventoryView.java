package edu.westga.cs3211.pirateship.view;

import java.time.LocalDate;

import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.Stock;
import edu.westga.cs3211.pirateship.viewmodel.ViewInventoryVM;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * Controller class for ViewInventory page.
 * 
 * @author Emi Collins
 * @version Fall 2025
 */
public class ViewInventoryView {

	@FXML
	private Button logoutButton;

	@FXML
	private Button removeButton;

	@FXML
	private Button backButton;

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

	@FXML
	private Label welcomeLable;

	private ViewInventoryVM viewModel;

	private Ship ship;

	@FXML
	void initialize() {
		assert this.welcomeLable != null
				: "fx:id=\"welcomeLable\" was not injected: check your FXML file 'ViewInventory.fxml'.";
		assert this.logoutButton != null
				: "fx:id=\"logoutButton\" was not injected: check your FXML file 'ViewInventory.fxml'.";

		this.setUpListenerForLogoutButton();
	}

	/**
	 * Sets the ship for the page.
	 * @param ship - the ship being set
	 */
	public void setShip(Ship ship) {
		this.ship = ship;
		this.viewModel = new ViewInventoryVM(ship);
		this.bindViewModel();
	}

	/**
	 * Sets the scene to show the landing page once more from back.
	 * 
	 * @param event - the back button being triggered
	 */
	public void returnToLandingPage(ActionEvent event) {
		try {
			this.viewModel.saveData();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(StockChangesView.class.getResource("LandingPage.fxml"));
			loader.load();

			LandingPage controller = loader.getController();
			controller.startup(this.viewModel.getShip().getCurrentUser());

			Parent parent = loader.getRoot();
			Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
			stage.setScene(new Scene(parent));
			stage.setTitle("Flying Dutchman – Home");
			stage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
			Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading Landing Page.");
			alert.showAndWait();
		}
	}
	
	public void removeSelectedStock() {
		var selectionModel = this.stockTable.getSelectionModel();
		var selectedStock = selectionModel.getSelectedItems();
		
		this.viewModel.removeStock(selectedStock);
	}

	private void bindViewModel() {
		this.welcomeLable.textProperty().bind(this.viewModel.welcomeMessageProperty());

		this.nameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));

		this.qtyCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getQuantity()));

		this.conditionCol
				.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCondition().toString()));

		this.specialCol.setCellValueFactory(
				cell -> new SimpleStringProperty(cell.getValue().getSpecialQualities().toString()));

		this.expCol.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getExpirationDate()));

		this.viewModel.loadEntireInventory(this.ship);

		this.stockTable.setItems(this.viewModel.getStocks());
	}

	/**
	 * Sets the up listener for logout button.
	 */
	private void setUpListenerForLogoutButton() {
		this.logoutButton.setOnAction((ActionEvent event) -> {
			try {
				this.viewModel.saveData();

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(LandingPage.class.getResource("LoginView.fxml"));
				loader.load();

				Parent parent = loader.getRoot();
				Scene scene = new Scene(parent);

				Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
				stage.setScene(scene);
				stage.setTitle("Login");
				stage.show();

			} catch (Exception ex) {
				ex.printStackTrace();
				Alert alert = new Alert(Alert.AlertType.ERROR, "Error logging user out.");
				alert.showAndWait();
			}
		});
	}
}
