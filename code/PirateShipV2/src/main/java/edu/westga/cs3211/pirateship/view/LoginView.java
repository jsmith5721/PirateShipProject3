package edu.westga.cs3211.pirateship.view;

import java.net.URL;
import java.util.ResourceBundle;

import edu.westga.cs3211.pirateship.viewmodel.LoginVM;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * The Class LoginView.
 * @author Justin Smith
 * @version Fall 2025
 */
public class LoginView {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button loginButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameText;
    private LoginVM viewModel;

    /**
     * Initialize.
     */
    @FXML
    void initialize() {
    	assert this.loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert this.passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert this.usernameText != null : "fx:id=\"usernameText\" was not injected: check your FXML file 'LoginView.fxml'.";

        this.viewModel = new LoginVM();

        this.usernameText.textProperty().bindBidirectional(this.viewModel.usernameProperty());
        this.passwordField.textProperty().bindBidirectional(this.viewModel.passwordProperty());
        
        this.setUpListenerForLoginButton();
        this.disableLoginButton();
    }
    
    /**
     * Sets the up listener for login button.
     */
    private void setUpListenerForLoginButton() {
		this.loginButton.setOnAction((ActionEvent event) -> {
			try {
				if (!this.viewModel.login()) {
	                Alert alert = new Alert(Alert.AlertType.ERROR);
	                alert.setTitle("Invalid Credentials");
	                alert.setHeaderText(null);
	                alert.setContentText("The username or password you entered is invalid.");
	                alert.showAndWait();
				} else {
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
			            Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading Landing Page.");
			            alert.showAndWait();
			        }
	            }
			} catch (IllegalArgumentException ex) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Credentials");
                alert.setHeaderText(null);
                alert.setContentText("The username and password can't be blank.");
                alert.showAndWait();
			}	
		});
	}
    
    /**
     * Disable login button.
     */
    private void disableLoginButton() {
		BooleanBinding anyRequieredFieldsEmpty = Bindings.or(this.usernameText.textProperty().isEmpty(), this.passwordField.textProperty().isEmpty());
		this.loginButton.disableProperty().bind(anyRequieredFieldsEmpty);
	}
}
