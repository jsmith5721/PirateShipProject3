package edu.westga.cs3211.pirateship.view;

import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.viewmodel.LandingPageVM;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * The Class LandingPage.
 * @author Justin Smith
 * @version Fall 2025
 */
public class LandingPage {
    @FXML
    private Button addStockButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button reviewStockButton;
    @FXML
    private Button addContainerButton;
    @FXML
    private Button viewInventoryButton;
    @FXML
    private Label welcomeLable;
    private LandingPageVM viewModel;

    /**
     * Initialize.
     */
    @FXML
    void initialize() {
		assert this.addStockButton != null : "fx:id=\"addStockButton\" was not injected: check your FXML file 'LandingPage.fxml'.";
		assert this.logoutButton != null : "fx:id=\"logoutButton\" was not injected: check your FXML file 'LandingPage.fxml'.";
		assert this.reviewStockButton != null : "fx:id=\"reviewStockButton\" was not injected: check your FXML file 'LandingPage.fxml'.";
		assert this.addContainerButton != null : "fx:id=\"addContainerButton\" was not injected: check your FXML file 'LandingPage.fxml'.";
		assert this.welcomeLable != null : "fx:id=\"welcomeLable\" was not injected: check your FXML file 'LandingPage.fxml'.";
	}
    
    /**
	 * Sets the ship and initializes the page.
	 *
	 * @param ship the new ship
	 */
    public void setShip(Ship ship) {
    	this.viewModel = new LandingPageVM(ship);
    	this.bindViewModel();
    }

    /**
     * Bind view model.
     */
    private void bindViewModel() {
        this.welcomeLable.textProperty().bind(this.viewModel.welcomeMessageProperty());

        this.reviewStockButton.visibleProperty().bind(this.viewModel.canReviewStockChangesProperty());
        this.reviewStockButton.managedProperty().bind(this.viewModel.canReviewStockChangesProperty());
        this.reviewStockButton.disableProperty().bind(this.viewModel.canReviewStockChangesProperty().not());
        
        
        this.setUpListenerForViewInventoryButton();
        this.setUpListenerForAddStockButton();
		this.setUpListenerForAddContainerButton();
		this.setUpListenerForReviewStockButton();
		this.setUpListenerForLogoutButton();
    }
    
    /**
     * Sets the up listener for add stock button.
     */
    private void setUpListenerForViewInventoryButton() {
		this.viewInventoryButton.setOnAction((ActionEvent event) -> {
			try {
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(LandingPage.class.getResource("ViewInventory.fxml"));
	            loader.load();
	            
	            ViewInventoryView controller = loader.getController();
	            // Debugging line to print containers
	            System.out.println("Landing to Add Stock: \n" + this.viewModel.getShip().getContainers());

	            Parent parent = loader.getRoot();
	            Scene scene = new Scene(parent);

	            Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
	            stage.setScene(scene);
	            stage.setTitle("Add Stock");
	            stage.show();

	        } catch (Exception ex) {
	            ex.printStackTrace();
	            Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading Add Stock Page.");
	            alert.showAndWait();
	        }
		});
	}
    
    /**
     * Sets the up listener for add stock button.
     */
    private void setUpListenerForAddStockButton() {
		this.addStockButton.setOnAction((ActionEvent event) -> {
			try {
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(LandingPage.class.getResource("AddStockView.fxml"));
	            loader.load();
	            
	            AddStockView controller = loader.getController();
	            controller.setShip(this.viewModel.getShip());
	            // Debugging line to print containers
	            System.out.println("Landing to Add Stock: \n" + this.viewModel.getShip().getContainers());

	            Parent parent = loader.getRoot();
	            Scene scene = new Scene(parent);

	            Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
	            stage.setScene(scene);
	            stage.setTitle("Add Stock");
	            stage.show();

	        } catch (Exception ex) {
	            ex.printStackTrace();
	            Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading Add Stock Page.");
	            alert.showAndWait();
	        }
		});
	}
    
    /**
     * Sets the up listener for add container button.
     */
    private void setUpListenerForAddContainerButton() {
		this.addContainerButton.setOnAction((ActionEvent event) -> {
			try {
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(LandingPage.class.getResource("AddContainerView.fxml"));
	            loader.load();
	            
	            AddContainerView controller = loader.getController();
	            controller.setShip(this.viewModel.getShip());

	            Parent parent = loader.getRoot();
	            Scene scene = new Scene(parent);

	            Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
	            stage.setScene(scene);
	            stage.setTitle("Add Container");
	            stage.show();

	        } catch (Exception ex) {
	            ex.printStackTrace();
	            Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading Add Container Page.");
	            alert.showAndWait();
	        }
		});
	}
    
    /**
     * Sets the up listener for review stock button.
     */
    private void setUpListenerForReviewStockButton() {
		this.reviewStockButton.setOnAction((ActionEvent event) -> {
			try {
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(LandingPage.class.getResource("StockChangesView.fxml"));
	            loader.load();
	            
	            StockChangesView controller = loader.getController();
	            controller.setShip(this.viewModel.getShip());

	            Parent parent = loader.getRoot();
	            Scene scene = new Scene(parent);

	            Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
	            stage.setScene(scene);
	            stage.setTitle("Stock Changes Report");
	            stage.show();

	        } catch (Exception ex) {
	            ex.printStackTrace();
	            Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading Stock Changes Report Page.");
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
}
