package org.example.test1.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import org.example.test1.Models.Admin;
import org.example.test1.Models.User;
import org.example.test1.Utils.SessionManager;
import org.example.test1.Utils.DatabaseConnection;

import java.io.IOException;
import java.sql.SQLException;

public class User_Admin_Controller {

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField userNameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerButton;

    @FXML
    private Hyperlink loginLink;

    @FXML
    private TextField loginUserNameField;

    @FXML
    private PasswordField loginPasswordField;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink registerLink;



    public void initialize() {
        if (registerButton != null) {
            registerButton.setOnAction(event -> handleRegister());
        }

        if (loginButton != null) {
            loginButton.setOnAction(event -> handleLogin());
        }

        if (loginLink != null) {
            loginLink.setOnAction(event -> navigateToLoginPage());
        }

        if (registerLink != null) {
            registerLink.setOnAction(event -> navigateToRegistrationPage());
        }

    }

    @FXML
    private void handleRegister()  {
        String fullName = fullNameField.getText();
        String userName = userNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        // Email validation regex
        String emailValidation = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        if (fullName.isEmpty() || userName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "All fields must be filled in.");
            return;
        }

        // Validate email format
        if (!email.matches(emailValidation)) {
            showAlert("Error", "Please enter a valid email address.");
            return;
        }

        try {
            User user = new User(0, fullName, userName, password, email);
            if (user.register()) {
                showAlert("Success", "User registered successfully!");
                clearFields("register");
                navigateToLoginPageFromRegister();
            }
        } catch (Exception e) {
            showAlert("Error", e.getMessage());
        }
    }


    @FXML
    private void handleLogin() {
        String userName = loginUserNameField.getText();
        String password = loginPasswordField.getText();

        if (userName.isEmpty() || password.isEmpty()) {
            showAlert("Error", "All fields must be filled in.");
            return;
        }

        try {

            if (DatabaseConnection.validateUser(userName, password)) {

                int userId = DatabaseConnection.getUserIdByUsername(userName);


                SessionManager.setLoggedUserId(userId);

                showAlert("Success", "User login successful!");
                clearFields("login");
                navigateToUserArticlePageFromLogin();
            } else if (Admin.adminLogin(userName, password)) {
                showAlert("Success", "Admin login successful!");
                clearFields("login");
                navigateToAdminPage();
            } else {
                showAlert("Error", "Username or password does not match.");
                clearFields("login");
            }
        } catch (SQLException e) {
            showAlert("Error", e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields(String formType) {
        if (formType.equals("login")) {
            loginUserNameField.clear();
            loginPasswordField.clear();
        } else if (formType.equals("register")) {
            fullNameField.clear();
            userNameField.clear();
            emailField.clear();
            passwordField.clear();
        }
    }

    @FXML
    private void navigateToLoginPage() {
        try {
            AnchorPane loginRoot = FXMLLoader.load(getClass().getResource("/org/example/test1/fxml files/LoginPage.fxml"));
            Scene loginScene = new Scene(loginRoot);

            Stage stage = (Stage) loginLink.getScene().getWindow();
            stage.setScene(loginScene);
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Failed to load the login page.");
        }
    }

    @FXML
    private void navigateToRegistrationPage() {
        try {
            AnchorPane registerRoot = FXMLLoader.load(getClass().getResource("/org/example/test1/fxml files/UserRegistration.fxml"));
            Scene registerScene = new Scene(registerRoot);

            Stage stage = (Stage) registerLink.getScene().getWindow();
            stage.setScene(registerScene);
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Failed to load the registration page.");
        }
    }

    private void navigateToLoginPageFromRegister() {
        try {
            AnchorPane articleRoot = FXMLLoader.load(getClass().getResource("/org/example/test1/fxml files/LoginPage.fxml"));
            Scene articleScene = new Scene(articleRoot);


            if (registerButton != null) {
                Stage stage = (Stage) registerButton.getScene().getWindow();
                stage.setScene(articleScene);
                stage.show();
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to load the user article page.");
        }
    }

    private void navigateToUserArticlePageFromLogin() {
        try {
            AnchorPane articleRoot = FXMLLoader.load(getClass().getResource("/org/example/test1/fxml files/UserArticlePage.fxml"));
            Scene articleScene = new Scene(articleRoot);


            if (loginButton != null) {
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(articleScene);
                stage.show();
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to load the user article page.");
        }
    }

    private void navigateToAdminPage() {
        try {
            AnchorPane adminRoot = FXMLLoader.load(getClass().getResource("/org/example/test1/fxml files/AdminView.fxml"));
            Scene adminScene = new Scene(adminRoot);

            if (loginButton != null) {
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(adminScene);
                stage.show();
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to load the admin page.");
        }
    }

    public void handleRegisterButtonClick(ActionEvent event) {
    }


}
