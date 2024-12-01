package org.example.test1.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.test1.Models.Article;
import org.example.test1.Utils.DatabaseConnection;

import java.sql.SQLException;

public class UserReadArticleController {

    @FXML
    private TextField UsertitleField;

    @FXML
    private TextArea UsercontentArea;

    public void UserSetArticleDetails(int articleId) {
        try {
            Article article = DatabaseConnection.getArticleById(articleId);
            if (article != null) {
                UsertitleField.setText(article.getArticleName());
                UsercontentArea.setText(article.getArticleContent());
            }
        } catch (SQLException e) {
            System.err.println("Error loading article: " + e.getMessage());
        }
    }
}
