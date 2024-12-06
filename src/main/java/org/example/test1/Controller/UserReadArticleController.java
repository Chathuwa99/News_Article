package org.example.test1.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.test1.Models.Article;
import org.example.test1.Utils.DatabaseConnection;
import org.example.test1.Utils.SessionManager;

import java.sql.SQLException;


public class UserReadArticleController {


    @FXML
    private TextField UsertitleField; // TextField to display the title of the article.

    @FXML
    private TextArea UsercontentArea; // TextArea to display the content of the article.


    public void initialize() {
        int currentArticleId = SessionManager.getCurrentArticleId(); // Get the current article ID from the session.
        if (currentArticleId != -1) {
            UserSetArticleDetails(currentArticleId); // Load article details based on the ID.
        } else {
            System.err.println("No article selected."); // Log an error if no article is selected.
        }
    }


    void UserSetArticleDetails(int articleId) {
        try {
            Article article = DatabaseConnection.getArticleById(articleId); // Fetch article details from the database.
            if (article != null) {
                UsertitleField.setText(article.getArticleName()); // Set the article title.
                UsercontentArea.setText(article.getArticleContent()); // Set the article content.
            } else {
                System.err.println("Article not found with ID: " + articleId); // Log an error if the article is not found.
            }
        } catch (SQLException e) {
            System.err.println("Error loading article: " + e.getMessage()); // Log an error if there's an issue fetching the article.
        }
    }


    private void recordUserInteraction(int articleId, String userpreference) {
        int loggedUserId = SessionManager.getLoggedUserId(); // Get the logged-in user ID from the session.
        if (loggedUserId == -1) {
            System.err.println("No user is logged in."); // Log an error if no user is logged in.
            return;
        }

        try {
            // Record the user interaction in the database.
            boolean success = DatabaseConnection.recordUserInteraction(loggedUserId, articleId, userpreference);
            if (success) {
                System.out.println("Interaction recorded: User " + loggedUserId + " " + userpreference + " article ID: " + articleId);
            } else {
                System.err.println("Error recording interaction."); // Log an error if recording fails.
            }
        } catch (SQLException e) {
            System.err.println("Error recording interaction: " + e.getMessage()); // Log an error if there's a database issue.
        }
    }


    @FXML
    private void handleLikeButton() {
        int currentArticleId = SessionManager.getCurrentArticleId(); // Get the current article ID from the session.
        if (currentArticleId != -1) {
            recordUserInteraction(currentArticleId, "LIKE"); // Record the "LIKE" interaction.
        } else {
            System.err.println("No article selected."); // Log an error if no article is selected.
        }
    }


    @FXML
    private void handleDislikeButton() {
        int currentArticleId = SessionManager.getCurrentArticleId(); // Get the current article ID from the session.
        if (currentArticleId != -1) {
            recordUserInteraction(currentArticleId, "DISLIKE"); // Record the "DISLIKE" interaction.
        } else {
            System.err.println("No article selected."); // Log an error if no article is selected.
        }
    }


    @FXML
    private void handleReadButton() {
        int currentArticleId = SessionManager.getCurrentArticleId(); // Get the current article ID from the session.
        if (currentArticleId != -1) {
            recordUserInteraction(currentArticleId, "READ"); // Record the "READ" interaction.
        } else {
            System.err.println("No article selected."); // Log an error if no article is selected.
        }
    }
}
