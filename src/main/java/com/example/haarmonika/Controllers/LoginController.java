package com.example.haarmonika.Controllers;

import com.example.haarmonika.Database.DatabaseConnection;
import com.example.haarmonika.Database.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    private final Repository repository = new Repository();

    public boolean login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Username and password must not be empty.");
        }
        return repository.validateUser(username, password);
    }
}

