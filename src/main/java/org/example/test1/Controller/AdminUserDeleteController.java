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
    private Label fullNameLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Button deleteButton;

    private User user;

    public void setUserDetails(User user) {
        this.user = user;
        fullNameLabel.setText(user.getFullName());
        userNameLabel.setText(user.getUsername());
        emailLabel.setText(user.getEmail());
    }

    @FXML
    private void deleteUser() {
        if (user != null) {
            try {
                boolean success = DatabaseConnection.deleteUserById(user.getId());
                if (success) {
                    showAlert("Success", "User deleted successfully!");
                    deleteButton.getScene().getWindow().hide(); // Close the window
                } else {
                    showAlert("Error", "Failed to delete the user.");
                }
            } catch (SQLException e) {
                showAlert("Error", "An error occurred while deleting the user.");
                e.printStackTrace();
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
