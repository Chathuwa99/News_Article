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
    private Button viewRegisteredUsersButton;

    @FXML
    private Button viewArticlesButton;

    @FXML
    private Hyperlink clickHereLink;

    // Method to navigate to the login page when "Click here" is clicked
    @FXML
    public void navigateToLoginPage() {
        try {
            // Load the login scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/test1/fxml files/LoginPage.fxml"));
            AnchorPane loginPage = loader.load();
            Stage stage = (Stage) clickHereLink.getScene().getWindow();
            Scene scene = new Scene(loginPage);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to navigate to the UserList page when the "View Registered Users" button is clicked
    @FXML
    public void navigateToUserListPage() {
        try {
            // Load the UserList page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/test1/fxml files/UserList.fxml"));
            AnchorPane userListPage = loader.load();
            Stage stage = (Stage) viewRegisteredUsersButton.getScene().getWindow();
            Scene scene = new Scene(userListPage);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to navigate to the Admin Article Page when the "View Articles" button is clicked
    @FXML
    public void navigateToAdminArticlePage() {
        try {
            // Load the AdminArticlePage
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/test1/fxml files/AdminArticlePage.fxml"));
            AnchorPane adminArticlePage = loader.load();
            Stage stage = (Stage) viewArticlesButton.getScene().getWindow();
            Scene scene = new Scene(adminArticlePage);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
