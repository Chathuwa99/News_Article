package org.example.test1.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.test1.Models.Article;
import org.example.test1.Utils.DatabaseConnection;

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
    private Button technologyButton;

    @FXML
    private Button healthButton;

    @FXML
    private Button educationButton;

    @FXML
    private Button sportsButton;

    @FXML
    private Button aiButton;

    @FXML
    private Button financeButton;

    public void initialize() {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("articleName"));
        contentColumn.setCellValueFactory(new PropertyValueFactory<>("articleContent"));


        technologyButton.setOnAction(event -> loadArticlesByCategory("Technology"));
        healthButton.setOnAction(event -> loadArticlesByCategory("Health"));
        educationButton.setOnAction(event -> loadArticlesByCategory("Education"));
        sportsButton.setOnAction(event -> loadArticlesByCategory("Sports"));
        aiButton.setOnAction(event -> loadArticlesByCategory("AI"));
        financeButton.setOnAction(event -> loadArticlesByCategory("Finance"));
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
}
