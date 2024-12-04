package org.example.test1.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.test1.Models.Article;
import org.example.test1.Utils.DatabaseConnection;
import org.example.test1.Utils.SessionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserReadArticleController {

    @FXML
    private TextField UsertitleField;

    @FXML
    private TextArea UsercontentArea;


    public void initialize() {
        int currentArticleId = SessionManager.getCurrentArticleId();
        if (currentArticleId != -1) {
            UserSetArticleDetails(currentArticleId); // Load article details based on the I
        } else {

            System.err.println("No article selected.");
        }
    }


    void UserSetArticleDetails(int articleId) {
        try {
            Article article = DatabaseConnection.getArticleById(articleId);
            if (article != null) {
                UsertitleField.setText(article.getArticleName());
                UsercontentArea.setText(article.getArticleContent());
            } else {
                System.err.println("Article not found with ID: " + articleId);
            }
        } catch (SQLException e) {
            System.err.println("Error loading article: " + e.getMessage());
        }
    }


    private void recordUserInteraction(int articleId, String userpreference) {
        int loggedUserId = SessionManager.getLoggedUserId();
        if (loggedUserId == -1) {
            System.err.println("No user is logged in.");
            return;
        }

        try {
            boolean success = DatabaseConnection.recordUserInteraction(loggedUserId, articleId, userpreference);
            if (success) {
                System.out.println("Interaction recorded: User " + loggedUserId + " " + userpreference + " article ID: " + articleId);
            } else {
                System.err.println("Error recording interaction.");
            }
        } catch (SQLException e) {
            System.err.println("Error recording interaction: " + e.getMessage());
        }
    }


    @FXML
    private void handleLikeButton() {
        int currentArticleId = SessionManager.getCurrentArticleId();
        if (currentArticleId != -1) {

            recordUserInteraction(currentArticleId, "LIKE");
        } else {
            System.err.println("No article selected.");
        }
    }


    @FXML
    private void handleDislikeButton() {
        int currentArticleId = SessionManager.getCurrentArticleId();
        if (currentArticleId != -1) {

            recordUserInteraction(currentArticleId, "DISLIKE");
        } else {
            System.err.println("No article selected.");
        }
    }


    @FXML
    private void handleReadButton() {
        int currentArticleId = SessionManager.getCurrentArticleId();
        if (currentArticleId != -1) {
            recordUserInteraction(currentArticleId, "READ");
        } else {
            System.err.println("No article selected.");
        }
    }
}
