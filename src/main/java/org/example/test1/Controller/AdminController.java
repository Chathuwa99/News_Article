package org.example.test1.Controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;


public class AdminController {


    @FXML
    private Button viewRegisteredUsersButton; // Button to navigate to the list of registered users.

    @FXML
    private Button viewArticlesButton; // Button to navigate to the articles management page.

    @FXML
    private Hyperlink clickHereLink; // Hyperlink to log out and return to the login page.


    @FXML
    public void navigateToLoginPage() {
        try {
            // Load the Login Page layout from the FXML file.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/test1/fxml files/LoginPage.fxml"));
            AnchorPane loginPage = loader.load();

            // Get the current stage and set the scene to the Login Page.
            Stage stage = (Stage) clickHereLink.getScene().getWindow();
            Scene scene = new Scene(loginPage);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Print the stack trace to debug if there's an issue loading the FXML file.
            e.printStackTrace();
        }
    }


    @FXML
    public void navigateToUserListPage() {
        try {
            // Load the User List Page layout from the FXML file.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/test1/fxml files/UserList.fxml"));
            AnchorPane userListPage = loader.load();

            // Get the current stage and set the scene to the User List Page.
            Stage stage = (Stage) viewRegisteredUsersButton.getScene().getWindow();
            Scene scene = new Scene(userListPage);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Print the stack trace to debug if there's an issue loading the FXML file.
            e.printStackTrace();
        }
    }


    @FXML
    public void navigateToAdminArticlePage() {
        try {
            // Load the Admin Article Management Page layout from the FXML file.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/test1/fxml files/AdminArticlePage.fxml"));
            AnchorPane adminArticlePage = loader.load();

            // Get the current stage and set the scene to the Admin Article Management Page.
            Stage stage = (Stage) viewArticlesButton.getScene().getWindow();
            Scene scene = new Scene(adminArticlePage);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Print the stack trace to debug if there's an issue loading the FXML file.
            e.printStackTrace();
        }
    }
}
