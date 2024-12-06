package org.example.test1.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.test1.Models.User;
import org.example.test1.Utils.DatabaseConnection;

import java.sql.SQLException;


public class AdminUserDeleteController {


    @FXML
    private Label fullNameLabel; // Label to display the user's full name.

    @FXML
    private Label userNameLabel; // Label to display the user's username.

    @FXML
    private Label emailLabel; // Label to display the user's email address.

    @FXML
    private Button deleteButton; // Button to trigger the user deletion process.

    // Holds the user object for the current user being viewed/deleted.
    private User user;


    public void setUserDetails(User user) {
        this.user = user;

        // Populate labels with the user's details.
        fullNameLabel.setText(user.getFullName());
        userNameLabel.setText(user.getUsername());
        emailLabel.setText(user.getEmail());
    }


    @FXML
    private void deleteUser() {
        if (user != null) { // Check if the user object has been set.
            try {
                // Attempt to delete the user from the database using their ID.
                boolean success = DatabaseConnection.deleteUserById(user.getId());

                if (success) {
                    // If successful, display a success message and close the window.
                    showAlert("Success", "User deleted successfully!");
                    deleteButton.getScene().getWindow().hide(); // Close the current window.
                } else {
                    // Display an error message if the deletion fails.
                    showAlert("Error", "Failed to delete the user.");
                }
            } catch (SQLException e) {
                // Handle SQL exceptions and display an error message.
                showAlert("Error", "An error occurred while deleting the user.");
                e.printStackTrace(); // Print stack trace for debugging purposes.
            }
        }
    }


    private void showAlert(String title, String message) {
        // Create and configure an alert dialog.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null); // No header text for simplicity.
        alert.setContentText(message);
        alert.showAndWait(); // Display the alert and wait for user acknowledgment.
    }
}
