package edu.westga.cs3211.pirateship.view;

import edu.westga.cs3211.pirateship.model.Conditions;
import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.viewmodel.AddStockVM;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

/**
 * The Class AddStockView.
 * 
 * @author Justin Smith
 * @version Fall 2025
 */
public class AddStockView {
	@FXML
	private Button addStockButton;
	@FXML
	private ListView<Container> availableContainersListView;
	@FXML
	private ComboBox<Conditions> conditionComboBox;
	@FXML
	private DatePicker expirationDatePicker;
	@FXML
	private Label expirationLabel;
	@FXML
	private Button homeButton;
	@FXML
	private Button logoutButton;
	@FXML
	private TextField nameText;
	@FXML
	private TextField sizeText;
	@FXML
	private ListView<SpecialQualities> specialQualsListView;
	@FXML
	private TextField quantityText;
	private AddStockVM viewModel;

	/**
	 * Initialize.
	 */
	@FXML
	void initialize() {
		assert this.availableContainersListView != null : "fx:id=\"availableContainersListView\" was not injected.";
	}

	/**
	 * Sets the ship and initializes the page.
	 *
	 * @param ship the new ship
	 */
	public void setShip(Ship ship) {
		this.viewModel = new AddStockVM(ship);
		this.bindViewModel();
	}

	/**
	 * Bind view model.
	 */
	private void bindViewModel() {
		this.nameText.textProperty().bindBidirectional(this.viewModel.getNameProperty());
		this.sizeText.textProperty().bindBidirectional(this.viewModel.getSizeProperty(), new NumberStringConverter());
		this.quantityText.textProperty().bindBidirectional(this.viewModel.getQuantityProperty(),
				new NumberStringConverter());

		this.conditionComboBox.itemsProperty().bind(Bindings
				.createObjectBinding(() -> javafx.collections.FXCollections.observableArrayList(Conditions.values())));
		this.conditionComboBox.valueProperty().bindBidirectional(this.viewModel.getConditionProperty());

		this.expirationDatePicker.valueProperty().bindBidirectional(this.viewModel.getExpirationDateProperty());
		this.expirationLabel.visibleProperty().bind(this.viewModel.showExpirationProperty());
		this.expirationLabel.managedProperty().bind(this.viewModel.showExpirationProperty());
		this.expirationDatePicker.visibleProperty().bind(this.viewModel.showExpirationProperty());
		this.expirationDatePicker.managedProperty().bind(this.viewModel.showExpirationProperty());

		this.specialQualsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		this.specialQualsListView.itemsProperty().bind(this.viewModel.getSpecialQualitiesListProperty());

		this.specialQualsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
			this.viewModel.updateSelectedQualities(this.specialQualsListView.getSelectionModel().getSelectedItems());
		});

		this.availableContainersListView.itemsProperty().bind(this.viewModel.getContainerListProperty());

		this.availableContainersListView.getSelectionModel().selectedItemProperty()
				.addListener((obs, oldVal, newVal) -> {
					this.viewModel.getSelectedContainerProperty().set(newVal);
				});

		this.setUpListenerForAddStockButton();
		this.setUpListenerForLogoutButton();
		this.setUpListenerForHomeButton();
	}

	/**
	 * Sets the up listener for add stock button.
	 */
	private void setUpListenerForAddStockButton() {
		this.addStockButton.setOnAction(event -> {
			try {
				String result = this.viewModel.addStock();
				Alert alert = new Alert(Alert.AlertType.INFORMATION, result);
				alert.showAndWait();
			} catch (Exception ex) {
				Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
				alert.showAndWait();
			}
		});
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
				Alert alert = new Alert(Alert.AlertType.ERROR, "Error logging user out.");
				alert.showAndWait();
			}
		});
	}

	/**
	 * Sets the up listener for home button.
	 */
	private void setUpListenerForHomeButton() {
		this.homeButton.setOnAction((ActionEvent event) -> {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(LoginView.class.getResource("LandingPage.fxml"));
				loader.load();

				LandingPage controller = loader.getController();
				controller.setShip(this.viewModel.getShip());

				Parent parent = loader.getRoot();
				Scene scene = new Scene(parent);

				Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
				stage.setScene(scene);
				stage.setTitle("Flying Dutchman – Home");
				stage.show();

			} catch (Exception ex) {
				Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading Home Page.");
				alert.showAndWait();
			}
		});
	}
}
