package org.example.test1.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Modality;
import org.example.test1.Models.Article;
import org.example.test1.Utils.DatabaseConnection;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class AdminArticleController {


    @FXML
    private TableView<Article> articleTable; // Table to display articles.

    @FXML
    private TableColumn<Article, Integer> ID; // Column for the article ID.

    @FXML
    private TableColumn<Article, String> Article; // Column for the article title.

    @FXML
    private TableColumn<Article, String> Content; // Column for the article content.

    @FXML
    private TextField keywordTextField; // TextField for entering search keywords.

    @FXML
    private Button searchButton; // Button to trigger search functionality.

    @FXML
    private Button BackButton; // Button to navigate back to the admin page.

    @FXML
    private Button addArticlesButton; // Button to navigate to the Add Article page.


    public void initialize() {
        // Map the TableView columns to the fields of the Article model.
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Article.setCellValueFactory(new PropertyValueFactory<>("articleName"));
        Content.setCellValueFactory(new PropertyValueFactory<>("articleContent"));

        // Set the action for the search button to trigger a search by keyword.
        searchButton.setOnAction(event -> searchArticlesByKeyword());

        // Add a double-click event listener to the table rows.
        articleTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-click detection
                Article selectedArticle = articleTable.getSelectionModel().getSelectedItem();
                if (selectedArticle != null) {
                    openReadArticleWindow(selectedArticle.getId()); // Open the selected article for reading.
                }
            }
        });
    }


    @FXML
    private void navigateBackToAdminPage() {
        try {
            // Load the admin page layout from FXML.
            AnchorPane adminRoot = FXMLLoader.load(getClass().getResource("/org/example/test1/fxml files/AdminView.fxml"));
            Scene adminScene = new Scene(adminRoot);

            // Get the current stage and set the new scene.
            if (BackButton != null) {
                Stage stage = (Stage) BackButton.getScene().getWindow();
                stage.setScene(adminScene);
                stage.show();
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to load the admin page."); // Show error if loading fails.
        }
    }


    private void openReadArticleWindow(int articleId) {
        try {
            // Load the Read Article page layout from FXML.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/test1/fxml files/AdminReadArticle.fxml"));
            AnchorPane root = loader.load();

            // Pass the article details to the new controller.
            AdminReadArticleController controller = loader.getController();
            controller.setArticleDetails(articleId);
            controller.setParentController(this);

            // Set up and display the new stage (modal window).
            Stage stage = new Stage();
            stage.setTitle("Read Article");
            stage.initModality(Modality.APPLICATION_MODAL); // Block interaction with the main window.
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Failed to open the article."); // Show error if loading fails.
        }
    }


    @FXML
    private void navigateToAddArticlePage() {
        try {
            // Load the Add Article page layout from FXML.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/test1/fxml files/AdminAddArticlePage.fxml"));
            AnchorPane addArticleRoot = loader.load();

            // Get the current stage and set the new scene.
            Scene addArticleScene = new Scene(addArticleRoot);
            Stage currentStage = (Stage) addArticlesButton.getScene().getWindow();
            currentStage.setScene(addArticleScene);
            currentStage.show();
        } catch (IOException e) {
            showAlert("Error", "Failed to open the Add Article page."); // Show error if loading fails.
        }
    }


    private void searchArticlesByKeyword() {
        String keyword = keywordTextField.getText().trim(); // Get the keyword from the text field.
        if (keyword.isEmpty()) {
            showAlert("Error", "Please enter a keyword to search."); // Alert if no keyword is entered.
            return;
        }

        try {
            // Fetch articles from the database that match the keyword.
            List<Article> articles = DatabaseConnection.getArticlesByKeyword(keyword);

            // Convert the list to an ObservableList and update the TableView.
            ObservableList<Article> articleList = FXCollections.observableArrayList(articles);
            articleTable.setItems(articleList);
        } catch (SQLException e) {
            showAlert("Error", "Error loading articles: " + e.getMessage()); // Show error if database operation fails.
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
}
