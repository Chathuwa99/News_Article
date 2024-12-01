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
    private TableView<Article> articleTable;

    @FXML
    private TableColumn<Article, Integer> ID;

    @FXML
    private TableColumn<Article, String> Article;

    @FXML
    private TableColumn<Article, String> Content;

    @FXML
    private TextField keywordTextField;

    @FXML
    private Button searchButton;

    @FXML
    private Button BackButton;

    @FXML
    private Button addArticlesButton;

    public void initialize() {
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Article.setCellValueFactory(new PropertyValueFactory<>("articleName"));
        Content.setCellValueFactory(new PropertyValueFactory<>("articleContent"));

        searchButton.setOnAction(event -> searchArticlesByKeyword());

        articleTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Article selectedArticle = articleTable.getSelectionModel().getSelectedItem();
                if (selectedArticle != null) {
                    openReadArticleWindow(selectedArticle.getId());
                }
            }
        });
    }


    @FXML
    private void navigateBackToAdminPage() {
        try {
            AnchorPane adminRoot = FXMLLoader.load(getClass().getResource("/org/example/test1/fxml files/AdminView.fxml"));
            Scene adminScene = new Scene(adminRoot);

            if (BackButton != null) {
                Stage stage = (Stage) BackButton.getScene().getWindow();
                stage.setScene(adminScene);
                stage.show();
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to load the admin page.");
        }
    }

    private void openReadArticleWindow(int articleId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/test1/fxml files/AdminReadArticle.fxml"));
            AnchorPane root = loader.load();


            AdminReadArticleController controller = loader.getController();
            controller.setArticleDetails(articleId);
            controller.setParentController(this);

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
    private void navigateToAddArticlePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/test1/fxml files/AdminAddArticlePage.fxml"));
            AnchorPane addArticleRoot = loader.load();

            Scene addArticleScene = new Scene(addArticleRoot);
            Stage currentStage = (Stage) addArticlesButton.getScene().getWindow();
            currentStage.setScene(addArticleScene);
            currentStage.show();
        } catch (IOException e) {
            showAlert("Error", "Failed to open the Add Article page.");
        }
    }

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




    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
