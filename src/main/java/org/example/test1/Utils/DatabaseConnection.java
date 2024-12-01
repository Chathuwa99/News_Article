package org.example.test1.Utils;

import org.example.test1.Models.Article;
import org.example.test1.Models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/article_diary";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static boolean isUsernameTaken(String username) throws SQLException {
        String query = "SELECT username FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();
            return resultSet.next();
        }
    }

    public static boolean isEmailTaken(String email) throws SQLException {
        String query = "SELECT email FROM users WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet resultSet = stmt.executeQuery();
            return resultSet.next();
        }
    }

    public static boolean insertUser(String fullName, String username, String email, String password) throws SQLException {
        String query = "INSERT INTO users (full_name, username, email, password) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, fullName);
            stmt.setString(2, username);
            stmt.setString(3, email);
            stmt.setString(4, password);
            return stmt.executeUpdate() > 0;
        }
    }

    public static boolean validateUser(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet resultSet = stmt.executeQuery();
            return resultSet.next();
        }
    }

    public static boolean validateAdmin(String username, String password) throws SQLException {
        String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet resultSet = stmt.executeQuery();
            return resultSet.next();
        }
    }

    public static boolean deleteUserById(int userId) throws SQLException {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        }
    }


    public static boolean deleteArticleById(int articleId) throws SQLException {
        String query = "DELETE FROM article WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, articleId);
            return stmt.executeUpdate() > 0;
        }
    }

    public static boolean updateArticle(int articleId, String articleName, String articleContent) throws SQLException {
        String query = "UPDATE article SET articleName = ?, articleContent = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, articleName);
            stmt.setString(2, articleContent);
            stmt.setInt(3, articleId);
            return stmt.executeUpdate() > 0;
        }
    }

    public static boolean insertArticle(String title, String content) throws SQLException {
        String query = "INSERT INTO article (articleName, articleContent) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setString(3, content);
            return stmt.executeUpdate() > 0;
        }
    }

    public static List<User> getAllUsers() throws SQLException {
        String query = "SELECT id, full_name, username, password, email FROM users";
        List<User> userList = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String fullName = resultSet.getString("full_name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");

                userList.add(new User(id, fullName, username, password, email));
            }
        }
        return userList;
    }

    public static Article getArticleById(int articleId) throws SQLException {
        String query = "SELECT * FROM article WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, articleId);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String articleName = resultSet.getString("articleName");
                String articleContent = resultSet.getString("articleContent");
                String category = resultSet.getString("category");

                return new Article(id, articleName, articleContent, category);
            }
        }
        return null;
    }

    public static List<Article> getArticlesByKeyword(String keyword) throws SQLException {
        String query = "SELECT * FROM article WHERE articleName LIKE ? OR articleContent LIKE ?";
        List<Article> articles = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String articleName = resultSet.getString("articleName");
                String articleContent = resultSet.getString("articleContent");

                articles.add(new Article(id, articleName, articleContent, ""));
            }
        }
        return articles;
    }
}
