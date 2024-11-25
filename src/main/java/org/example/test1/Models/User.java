package org.example.test1.Models;

import org.example.test1.Utils.DatabaseConnection;

import java.sql.SQLException;

public class User {
    private String fullName;
    private String username;
    private String password;
    private String email;

    public User(String fullName, String username, String password, String email) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public boolean register() throws SQLException {
        if (DatabaseConnection.isUsernameTaken(this.username)) {
            throw new IllegalArgumentException("This username is already taken.");
        }

        if (DatabaseConnection.isEmailTaken(this.email)) {
            throw new IllegalArgumentException("This email is already registered.");
        }

        return DatabaseConnection.insertUser(this.fullName, this.username, this.email, this.password);
    }

    public static boolean login(String username, String password) throws SQLException {
        return DatabaseConnection.validateUser(username, password);
    }

    public static boolean adminLogin(String username, String password) throws SQLException {
        return DatabaseConnection.validateAdmin(username, password);
    }
}