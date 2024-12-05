package org.example.test1.Models;

import org.example.test1.Utils.DatabaseConnection;

import java.sql.SQLException;

public class Admin {
    private int id;
    private String username;
    private String password;

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
        return DatabaseConnection.validateAdmin(username, password);
    }
}
