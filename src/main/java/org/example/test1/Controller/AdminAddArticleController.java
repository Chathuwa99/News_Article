package org.example.test1.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.test1.Utils.DatabaseConnection;

import java.io.IOException;
import java.sql.SQLException;


public class AdminAddArticleController {


    @FXML
    private TextField titleField; // TextField for entering the article title.

    @FXML
    private TextArea contentArea; // TextArea for entering the article content.

    @FXML
    private Button addArticleButton; // Button to trigger adding the article.

    @FXML
    private Button backButton; // Button to navigate back to the previous page.


    @FXML
    private void initialize() {
        // Set the action for the "Add Article" button.
        addArticleButton.setOnAction(event -> addArticle());

        // Set the action for the "Back" button.
        backButton.setOnAction(event -> navigateBack());
    }


    private void addArticle() {
        // Get the input values from the UI fields.
        String title = titleField.getText().trim();
        String content = contentArea.getText().trim();

        // Validate that both fields are filled.
        if (title.isEmpty() || content.isEmpty()) {
            showAlert("Error", "All fields are required.");
            return;
        }

        try {
            // Attempt to insert the article into the database.
            boolean success = DatabaseConnection.insertArticle(title, content);

            // Show a success or error message based on the outcome.
            if (success) {
                showAlert("Success", "Article added successfully.");
                clearFields(); // Clear the input fields after successful submission.
            } else {
                showAlert("Error", "Failed to add the article.");
            }
        } catch (SQLException e) {
            // Handle SQL exceptions and show an error message.
            showAlert("Error", "An error occurred: " + e.getMessage());
        }
    }


    private void navigateBack() {
        try {
            // Load the FXML file for the Admin Article Page.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/test1/fxml files/AdminArticlePage.fxml"));

            // Get the current stage and set the new scene.
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            currentStage.setScene(new Scene(loader.load()));
            currentStage.show();
        } catch (IOException e) {
            // Handle IO exceptions and show an error message.
            showAlert("Error", "Failed to navigate back to the article page.");
        }
    }


    private void showAlert(String title, String message) {
        // Create and configure an alert dialog.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null); // No header text for simplicity.
        alert.setContentText(message);
        alert.showAndWait(); // Wait for the user to close the alert.
    }


    private void clearFields() {
        // Clear the text fields for title and content.
        titleField.clear();
        contentArea.clear();
    }
}
