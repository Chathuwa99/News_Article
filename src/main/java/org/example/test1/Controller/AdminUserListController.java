package org.example.test1.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.test1.Models.User;
import org.example.test1.Utils.DatabaseConnection;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class AdminUserListController {


    @FXML
    private TableView<User> userlistTable; // TableView for displaying a list of users.

    @FXML
    private TableColumn<User, Integer> ID; // Column for displaying user IDs.

    @FXML
    private TableColumn<User, String> FullName; // Column for displaying users' full names.

    @FXML
    private TableColumn<User, String> UserName; // Column for displaying usernames.

    @FXML
    private TableColumn<User, String> Email; // Column for displaying users' email addresses.

    @FXML
    private javafx.scene.control.Button BackButton; // Button for navigating back to the admin page.


    @FXML
    public void initialize() {
        // Link table columns to the corresponding properties of the User model.
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        FullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        UserName.setCellValueFactory(new PropertyValueFactory<>("username"));
        Email.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Load user data into the table when the controller is initialized.
        try {
            loadUserData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Add a double-click listener to the table rows.
        userlistTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Triggered when a row is double-clicked.
                User selectedUser = userlistTable.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    openUserDetails(selectedUser); // Open the user details window for the selected user.
                }
            }
        });
    }


    private void loadUserData() throws SQLException {
        // Fetch all users from the database.
        List<User> users = DatabaseConnection.getAllUsers();

        // Convert the list to an ObservableList for TableView compatibility.
        ObservableList<User> userObservableList = FXCollections.observableArrayList(users);

        // Populate the TableView with user data.
        userlistTable.setItems(userObservableList);
    }


    private void openUserDetails(User user) {
        try {
            // Load the FXML file for the user details page.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/test1/fxml files/AdminUserDelete.fxml"));
            AnchorPane userDetailsRoot = loader.load();

            // Pass the user details to the AdminUserDeleteController.
            AdminUserDeleteController controller = loader.getController();
            controller.setUserDetails(user);

            // Create a new stage for displaying the user details page.
            Stage stage = new Stage();
            stage.setTitle("Delete User");
            stage.setScene(new Scene(userDetailsRoot));
            stage.show();
        } catch (IOException e) {
            // Show an error alert if the FXML file cannot be loaded.
            showAlert("Error", "Failed to load the user details page.");
        }
    }


    public void navigateBackToAdminPage() {
        try {
            // Load the admin page FXML.
            AnchorPane adminRoot = FXMLLoader.load(getClass().getResource("/org/example/test1/fxml files/AdminView.fxml"));

            // Create a new scene for the admin page.
            Scene adminScene = new Scene(adminRoot);

            if (BackButton != null) { // Check if the back button is present.
                // Get the current stage and set the new scene.
                Stage stage = (Stage) BackButton.getScene().getWindow();
                stage.setScene(adminScene);
                stage.show();
            }
        } catch (IOException e) {
            // Show an error alert if the FXML file cannot be loaded.
            showAlert("Error", "Failed to load the admin page.");
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null); // No header for a cleaner appearance.
        alert.setContentText(message);
        alert.showAndWait(); // Wait for the user to acknowledge the alert.
    }
}
