package org.example.test1.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.test1.Models.Article;
import org.example.test1.Utils.DatabaseConnection;

import java.sql.SQLException;


public class AdminReadArticleController {


    @FXML
    private TextField titleField; // TextField to display and edit the article title.

    @FXML
    private TextArea contentArea; // TextArea to display and edit the article content.

    @FXML
    private Button saveEditsButton; // Button to save any edits made to the article.

    @FXML
    private Button deleteButton; // Button to delete the current article.

    // Holds the ID of the current article being edited.
    private int currentArticleId;

    // Reference to the parent controller (AdminArticleController) for refreshing the article list after changes.
    private AdminArticleController parentController;


    public void setArticleDetails(int articleId) {
        this.currentArticleId = articleId;
        try {
            // Fetch the article from the database using its ID.
            Article article = DatabaseConnection.getArticleById(articleId);

            if (article != null) {
                // Populate the UI fields with the article details.
                titleField.setText(article.getArticleName());
                contentArea.setText(article.getArticleContent());
            }
        } catch (SQLException e) {
            // Show an error message if the article could not be loaded.
            showAlert("Error", "Failed to load article: " + e.getMessage());
        }
    }


    public void setParentController(AdminArticleController parentController) {
        this.parentController = parentController;
    }


    @FXML
    private void handleDeleteArticle() {
        try {
            // Delete the article from the database using its ID.
            boolean success = DatabaseConnection.deleteArticleById(currentArticleId);

            if (success) {
                // Show a success message and close the window if the article was deleted.
                showAlert("Success", "Article deleted successfully.");
                closeWindow();
            } else {
                // Show an error message if the deletion failed.
                showAlert("Error", "Failed to delete the article.");
            }
        } catch (SQLException e) {
            // Handle any SQL-related errors.
            showAlert("Error", "An error occurred: " + e.getMessage());
        }
    }


    @FXML
    private void handleSaveEdits() {
        // Get the updated title and content from the UI fields.
        String updatedTitle = titleField.getText();
        String updatedContent = contentArea.getText();

        try {
            // Update the article in the database with the new details.
            boolean success = DatabaseConnection.updateArticle(currentArticleId, updatedTitle, updatedContent);

            if (success) {
                // Show a success message and close the window if the update was successful.
                showAlert("Success", "Article updated successfully.");
                closeWindow();
            } else {
                // Show an error message if the update failed.
                showAlert("Error", "Failed to update the article.");
            }
        } catch (SQLException e) {
            // Handle any SQL-related errors.
            showAlert("Error", "An error occurred: " + e.getMessage());
        }
    }


    private void showAlert(String title, String message) {
        // Create and configure the alert dialog.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null); // No header text for simplicity.
        alert.setContentText(message);
        alert.showAndWait(); // Display the alert and wait for user acknowledgment.
    }


    private void closeWindow() {
        // Get the current stage (window) and close it.
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }
}
