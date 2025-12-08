package edu.westga.cs3211.pirateship.view;

import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.StockType;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.viewmodel.AddContainerVM;
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
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * The Class AddContainerView.
 * 
 * @author Justin Smith
 * @version Fall 2025
 */
public class AddContainerView {
	@FXML
	private Button addContainerButton;
	@FXML
	private TextField containerSizeText;
	@FXML
	private Button homeButton;
	@FXML
	private Button logoutButton;
	@FXML
	private ComboBox<StockType> stockTypeComboBox;
	@FXML
	private ListView<SpecialQualities> specialQualsListView;
	private AddContainerVM viewModel;

	/**
	 * Initialize.
	 */
	@FXML
	void initialize() {
		assert this.addContainerButton != null : "fx:id=\"addContainerButton\" was not injected.";
		assert this.containerSizeText != null : "fx:id=\"containerSizeText\" was not injected.";
		assert this.homeButton != null : "fx:id=\"homeButton\" was not injected.";
		assert this.logoutButton != null : "fx:id=\"logoutButton\" was not injected.";
		assert this.specialQualsListView != null : "fx:id=\"specialQualsListView\" was not injected.";
		assert this.stockTypeComboBox != null : "fx:id=\"stockTypeComboBox\" was not injected: check your FXML file 'AddContainerView.fxml'.";
	}

	/**
	 * Sets the ship.
	 *
	 * @param currentUser currently logged in user
	 */
	public void startup(User currentUser) {
		this.viewModel = new AddContainerVM(currentUser);
		this.bindViewModel();
	}

	/**
	 * Bind view model.
	 */
	private void bindViewModel() {

		this.containerSizeText.textProperty().bindBidirectional(this.viewModel.getSizeProperty(),
				new javafx.util.converter.NumberStringConverter());
		
		this.stockTypeComboBox.itemsProperty().bind(Bindings
				.createObjectBinding(() -> javafx.collections.FXCollections.observableArrayList(StockType.values())));
		this.stockTypeComboBox.valueProperty().bindBidirectional(this.viewModel.getStockTypeProperty());

		this.specialQualsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		this.specialQualsListView.itemsProperty().bind(this.viewModel.getSpecialQualityProperty());

		this.specialQualsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			this.viewModel.updateSelectedQualities(this.specialQualsListView.getSelectionModel().getSelectedItems());
		});

		this.setUpListenerForAddContainerButton();
		this.setUpListenerForLogoutButton();
		this.setUpListenerForHomeButton();
	}

	/**
	 * Sets the up listener for add container button.
	 */
	private void setUpListenerForAddContainerButton() {
		this.addContainerButton.setOnAction((ActionEvent event) -> {
			try {
				String result = this.viewModel.addContainer();
				if (!result.isEmpty()) {
					Alert alert = new Alert(Alert.AlertType.INFORMATION, result + " added successfully.");
					alert.showAndWait();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				Alert alert = new Alert(Alert.AlertType.ERROR, "Error adding Container.");
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
				ex.printStackTrace();
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
				this.viewModel.saveData();
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(LoginView.class.getResource("LandingPage.fxml"));
				loader.load();
				
				LandingPage controller = loader.getController();
				controller.startup(this.viewModel.getShip().getCurrentUser());

				Parent parent = loader.getRoot();
				Scene scene = new Scene(parent);

				Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
				stage.setScene(scene);
				stage.setTitle("Flying Dutchman – Home");
				stage.show();

			} catch (Exception ex) {
				ex.printStackTrace();
				Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading Home Page.");
				alert.showAndWait();
			}
		});
	}
}
