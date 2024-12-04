package org.example.test1.Utils;

public class SessionManager {
    private static int loggedUserId; // Default to -1 if no user is logged in
    private static int currentArticleId = -1; // Default value for no article selected

    // Getter for logged user ID
    public static int getLoggedUserId() {
        return loggedUserId;
    }

    // Setter for logged user ID
    public static void setLoggedUserId(int userId) {
        loggedUserId = userId;
    }

    // Getter for current article ID
    public static int getCurrentArticleId() {
        return currentArticleId;
    }

    // Setter for current article ID
    public static void setCurrentArticleId(int articleId) {
        currentArticleId = articleId;
    }
}
