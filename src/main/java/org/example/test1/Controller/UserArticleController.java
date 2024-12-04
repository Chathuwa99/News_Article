package org.example.test1.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.test1.Models.Article;
import org.example.test1.Utils.ArticleRecommender;
import org.example.test1.Utils.DatabaseConnection;
import org.example.test1.Utils.SessionManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserArticleController {

    @FXML
    private TableView<Article> articleTable;

    @FXML
    private TableColumn<Article, Integer> idColumn;

    @FXML
    private TableColumn<Article, String> nameColumn;

    @FXML
    private TableColumn<Article, String> contentColumn;

    @FXML
    private TableColumn<Article, Integer> RecIdColumn;

    @FXML
    private TableColumn<Article, String> RecNameColumn;

    @FXML
    private TableColumn<Article, String> RecContentColumn;

    @FXML
    private TextField keywordTextField;

    @FXML
    private Button searchButton;

    @FXML
    private Button backButton;

    @FXML
    private Button recommendedArticlesButton;

    private boolean isRecommendationPage = false;

    public void initialize() {
        // Setup for main article page
        if (idColumn != null) {
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("articleName"));
            contentColumn.setCellValueFactory(new PropertyValueFactory<>("articleContent"));

            searchButton.setOnAction(event -> searchArticlesByKeyword());

            articleTable.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    Article selectedArticle = articleTable.getSelectionModel().getSelectedItem();
                    if (selectedArticle != null) {
                        int articleId = selectedArticle.getId();
                        SessionManager.setCurrentArticleId(articleId);
                        openUserReadArticleWindow(selectedArticle.getId());
                    }
                }
            });
        }

        // Setup for recommendation page
        if (RecIdColumn != null) {
            RecIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            RecNameColumn.setCellValueFactory(new PropertyValueFactory<>("articleName"));
            RecContentColumn.setCellValueFactory(new PropertyValueFactory<>("articleContent"));

            loadRecommendedArticles();

            articleTable.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    Article selectedArticle = articleTable.getSelectionModel().getSelectedItem();
                    if (selectedArticle != null) {
                        int articleId = selectedArticle.getId();
                        SessionManager.setCurrentArticleId(articleId);
                        openUserReadArticleWindow(selectedArticle.getId());
                    }
                }
            });
        }
    }

    @FXML
    private void navigateToRecommendedArticles() {
        try {
            AnchorPane recommendedRoot = FXMLLoader.load(getClass().getResource("/org/example/test1/fxml files/RecommendationPage.fxml"));
            Scene recommendedScene = new Scene(recommendedRoot);

            Stage stage = (Stage) recommendedArticlesButton.getScene().getWindow();
            stage.setScene(recommendedScene);
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Failed to load the recommended articles page.");
        }
    }

    private void loadRecommendedArticles() {
        try {
            // Get recommended articles based on user's interaction history
            List<Article> recommendedArticles = ArticleRecommender.recommendArticles(10); // Get top 10 recommendations

            if (recommendedArticles.isEmpty()) {
                showAlert("Recommendations", "No recommendations available at the moment.");
                return;
            }

            ObservableList<Article> recommendationList = FXCollections.observableArrayList(recommendedArticles);
            articleTable.setItems(recommendationList);
        } catch (Exception e) {
            showAlert("Error", "Failed to load recommendations: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Existing methods like searchArticlesByKeyword(), openUserReadArticleWindow(), etc. remain the same
    private void searchArticlesByKeyword() {
        String keyword = keywordTextField.getText().trim();
        if (keyword.isEmpty()) {
            showAlert("Error", "Please enter a keyword to search.");
            return;
        }

        try {
            List<Article> articles = DatabaseConnection.getArticlesByKeyword(keyword);
            ObservableList<Article> articleList = FXCollections.observableArrayList(articles);
            articleTable.setItems(articleList);
        } catch (SQLException e) {
            showAlert("Error", "Error loading articles: " + e.getMessage());
        }
    }

    private void openUserReadArticleWindow(int articleId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/test1/fxml files/UserReadArticle.fxml"));
            AnchorPane root = loader.load();

            UserReadArticleController controller = loader.getController();
            controller.UserSetArticleDetails(articleId);

            Stage stage = new Stage();
            stage.setTitle("Read Article");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Failed to open the article.");
        }
    }

    @FXML
    private void navigateBackToLoginPage() {
        try {
            AnchorPane adminRoot = FXMLLoader.load(getClass().getResource("/org/example/test1/fxml files/LoginPage.fxml"));
            Scene adminScene = new Scene(adminRoot);

            if (backButton != null) {
                Stage stage = (Stage) backButton.getScene().getWindow();
                stage.setScene(adminScene);
                stage.show();
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to load the login page.");
        }
    }

    @FXML
    private void NavigateToArticlePageFromRecPage() {
        try {
            // Load the UserArticlePage.fxml file
            AnchorPane userArticlePageRoot = FXMLLoader.load(getClass().getResource("/org/example/test1/fxml files/UserArticlePage.fxml"));

            // Get the current stage
            Stage currentStage = (Stage) backButton.getScene().getWindow();

            // Set the new scene
            Scene userArticlePageScene = new Scene(userArticlePageRoot);
            currentStage.setScene(userArticlePageScene);
            currentStage.show();
        } catch (IOException e) {
            showAlert("Error", "Failed to load the article page.");
            e.printStackTrace();
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