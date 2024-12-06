package org.example.test1.Models;

import org.example.test1.Utils.DatabaseConnection;

import java.sql.SQLException;


public class User {

    // Fields representing user details.
    private int id;             // Unique identifier for the user.
    private String fullName;    // Full name of the user.
    private String username;    // Username chosen by the user.
    private String password;    // Password for the user account.
    private String email;       // Email address of the user.


    public User(int id, String fullName, String username, String password, String email) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Getters for user details.


    public int getId() {
        return id;
    }


    public String getFullName() {
        return fullName;
    }


    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }


    public String getEmail() {
        return email;
    }


    public boolean register() throws SQLException {
        // Check if the username is already taken.
        if (DatabaseConnection.isUsernameTaken(this.username)) {
            throw new IllegalArgumentException("This username is already taken.");
        }

        // Check if the email is already registered.
        if (DatabaseConnection.isEmailTaken(this.email)) {
            throw new IllegalArgumentException("This email is already registered.");
        }

        // Insert the user's details into the database.
        return DatabaseConnection.insertUser(this.fullName, this.username, this.email, this.password);
    }


    public static boolean login(String username, String password) throws SQLException {
        return DatabaseConnection.validateUser(username, password);
    }


}
