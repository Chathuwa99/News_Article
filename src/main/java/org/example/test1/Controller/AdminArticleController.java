package org.example.test1.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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
    private Button AdmintechnologyButton;

    @FXML
    private Button AdminhealthButton;

    @FXML
    private Button AdmineducationButton;

    @FXML
    private Button AdminsportsButton;

    @FXML
    private Button AdminaiButton;

    @FXML
    private Button AdminfinanceButton;

    @FXML
    private Button BackButton;

    public void initialize() {

        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Article.setCellValueFactory(new PropertyValueFactory<>("articleName"));
        Content.setCellValueFactory(new PropertyValueFactory<>("articleContent"));


        AdmintechnologyButton.setOnAction(event -> loadArticlesByCategory("Technology"));
        AdminhealthButton.setOnAction(event -> loadArticlesByCategory("Health"));
        AdmineducationButton.setOnAction(event -> loadArticlesByCategory("Education"));
        AdminsportsButton.setOnAction(event -> loadArticlesByCategory("Sports"));
        AdminaiButton.setOnAction(event -> loadArticlesByCategory("AI"));
        AdminfinanceButton.setOnAction(event -> loadArticlesByCategory("Finance"));
    }

    private void loadArticlesByCategory(String category) {
        try {
            List<Article> articles = DatabaseConnection.getArticlesByCategory(category);
            ObservableList<Article> articleList = FXCollections.observableArrayList(articles);
            articleTable.setItems(articleList);
        } catch (SQLException e) {
            System.err.println("Error loading articles: " + e.getMessage());
        }
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



}








