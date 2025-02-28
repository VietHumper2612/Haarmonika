package com.example.haarmonika.Controllers;

import com.example.haarmonika.Database.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    public boolean login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Username and password must not be empty.");
        }

        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        if (databaseConnection != null) {
            try {
                String mysqlUser = "SELECT * FROM user WHERE username = ? AND password = ?";
                PreparedStatement PPSM = databaseConnection.getConnection().prepareStatement(mysqlUser);
                PPSM.setString(1, username);
                PPSM.setString(2, password);
                ResultSet resultSet = PPSM.executeQuery();
                return resultSet.next();

            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }
        return false;
    }
}
