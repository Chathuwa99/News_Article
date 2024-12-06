package org.example.test1.Models;

import org.example.test1.Utils.DatabaseConnection;

import java.sql.SQLException;


public class Admin {

    // Fields to store admin information.
    private int id;          // Admin ID
    private String username; // Admin username
    private String password; // Admin password


    public Admin(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }


    public int getId() {
        return id;
    }


    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }


    public static boolean adminLogin(String username, String password) throws SQLException {
        return DatabaseConnection.validateAdmin(username, password); // Call utility method to validate admin credentials.
    }
}
