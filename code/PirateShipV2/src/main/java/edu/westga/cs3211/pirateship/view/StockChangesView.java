package edu.westga.cs3211.pirateship.view;

import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.Transaction;
import edu.westga.cs3211.pirateship.viewmodel.StockChangesVM;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

/**
 * The Class StockChangesView.
 * 
 * @author Justin Smith
 * @version Fall 2025
 */
public class StockChangesView {
	@FXML
	private Button homeButton;
	@FXML
	private Button logoutButton;
	@FXML
	private DatePicker startDatePicker;
	@FXML
	private DatePicker endDatePicker;
	@FXML
	private ListView<SpecialQualities> specialQualsListView;
	@FXML
	private ComboBox<String> crewmemberComboBox;
	@FXML
	private Button filterButton;
	@FXML
	private ListView<Transaction> stockChangesListView;

	private StockChangesVM viewModel;

	/**
	 * Initialize.
	 */
	@FXML
	void initialize() {
		assert this.homeButton != null;
		assert this.logoutButton != null;
		assert this.startDatePicker != null;
		assert this.endDatePicker != null;
		assert this.specialQualsListView != null;
		assert this.crewmemberComboBox != null;
		assert this.filterButton != null;
		assert this.stockChangesListView != null;
	}

	/**
	 * Sets the ship and initializes the page.
	 *
	 * @param ship the new ship
	 */
	public void setShip(Ship ship) {
		this.viewModel = new StockChangesVM(ship);
		this.bindViewModel();
	}

	/**
	 * Bind view model.
	 */
	private void bindViewModel() {
		this.specialQualsListView.setItems(this.viewModel.specialQualitiesListProperty());
		this.crewmemberComboBox.itemsProperty()
				.bind(Bindings.createObjectBinding(() -> this.viewModel.crewmemberListProperty()));

		this.stockChangesListView.itemsProperty().bind(this.viewModel.filteredTransactionsProperty());

		this.startDatePicker.valueProperty().bindBidirectional(this.viewModel.startDateProperty());
		this.endDatePicker.valueProperty().bindBidirectional(this.viewModel.endDateProperty());

		this.specialQualsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		this.specialQualsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
			this.viewModel.selectedSpecialQualitiesProperty()
					.set(this.specialQualsListView.getSelectionModel().getSelectedItems());
		});

		this.crewmemberComboBox.valueProperty().bindBidirectional(this.viewModel.selectedCrewmemberProperty());

		this.setupListenerOnFilterButton();
		this.setupListenerOnHomeButton();
		this.setupListenerOnLogoutButton();
	}

	/**
	 * Setup listener on filter button.
	 */
	private void setupListenerOnFilterButton() {
		this.filterButton.setOnAction(event -> {
			this.viewModel.applyFilter();
		});
	}

	/**
	 * Setup listener on logout button.
	 */
	private void setupListenerOnLogoutButton() {
		this.logoutButton.setOnAction(event -> {
			try {
				this.viewModel.getShip().saveData();

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(StockChangesView.class.getResource("LoginView.fxml"));
				loader.load();

				Parent parent = loader.getRoot();
				Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
				stage.setScene(new Scene(parent));
				stage.setTitle("Login");
				stage.show();

			} catch (Exception ex) {
				ex.printStackTrace();
				new Alert(Alert.AlertType.ERROR, "Error logging out").showAndWait();
			}
		});
	}

	/**
	 * Setup listener on home button.
	 */
	private void setupListenerOnHomeButton() {
		this.homeButton.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(StockChangesView.class.getResource("LandingPage.fxml"));
				loader.load();

				LandingPage controller = loader.getController();
				controller.setShip(this.viewModel.getShip());

				Parent parent = loader.getRoot();
				Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
				stage.setScene(new Scene(parent));
				stage.setTitle("Flying Dutchman – Home");
				stage.show();

			} catch (Exception ex) {
				ex.printStackTrace();
				new Alert(Alert.AlertType.ERROR, "Error loading Home Page").showAndWait();
			}
		});
	}
}
