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
    private TextField titleField;

    @FXML
    private TextArea contentArea;

    @FXML
    private Button addArticleButton;

    @FXML
    private Button backButton;

    @FXML
    private void initialize() {
        addArticleButton.setOnAction(event -> addArticle());
        backButton.setOnAction(event -> navigateBack());
    }

    private void addArticle() {
        String title = titleField.getText().trim();
        String content = contentArea.getText().trim();

        if (title.isEmpty() || content.isEmpty()) {
            showAlert("Error", "All fields are required.");
            return;
        }

        try {
            boolean success = DatabaseConnection.insertArticle(title,content);
            if (success) {
                showAlert("Success", "Article added successfully.");
                clearFields();
            } else {
                showAlert("Error", "Failed to add the article.");
            }
        } catch (SQLException e) {
            showAlert("Error", "An error occurred: " + e.getMessage());
        }
    }

    private void navigateBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/test1/fxml files/AdminArticlePage.fxml"));
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            currentStage.setScene(new Scene(loader.load()));
            currentStage.show();
        } catch (IOException e) {
            showAlert("Error", "Failed to navigate back to the article page.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        titleField.clear();
        contentArea.clear();
    }
}
