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
    private TextField titleField;

    @FXML
    private TextArea contentArea;

    @FXML
    private Button saveEditsButton;

    @FXML
    private Button deleteButton;

    private int currentArticleId;
    private AdminArticleController parentController;

    public void setArticleDetails(int articleId) {
        this.currentArticleId = articleId;
        try {
            Article article = DatabaseConnection.getArticleById(articleId);
            if (article != null) { 
                titleField.setText(article.getArticleName());
                contentArea.setText(article.getArticleContent());
            }
        } catch (SQLException e) {
            showAlert("Error", "Failed to load article: " + e.getMessage());
        }
    }

    public void setParentController(AdminArticleController parentController) {
        this.parentController = parentController;
    }

    @FXML
    private void handleDeleteArticle() {
        try {
            boolean success = DatabaseConnection.deleteArticleById(currentArticleId);
            if (success) {
                showAlert("Success", "Article deleted successfully.");

                closeWindow();
            } else {
                showAlert("Error", "Failed to delete the article.");
            }
        } catch (SQLException e) {
            showAlert("Error", "An error occurred: " + e.getMessage());
        }
    }

    @FXML
    private void handleSaveEdits() {
        String updatedTitle = titleField.getText();
        String updatedContent = contentArea.getText();

        try {
            boolean success = DatabaseConnection.updateArticle(currentArticleId, updatedTitle, updatedContent);
            if (success) {
                showAlert("Success", "Article updated successfully.");

                closeWindow();
            } else {
                showAlert("Error", "Failed to update the article.");
            }
        } catch (SQLException e) {
            showAlert("Error", "An error occurred: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }
}
