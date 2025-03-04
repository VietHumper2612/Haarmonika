package com.example.haarmonika.Controllers;

import com.example.haarmonika.Database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    public void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Username and password must not be empty.");
            return;
        }

        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        if (databaseConnection != null) {
            try {
                String mysqlUser = "SELECT * FROM user WHERE username = ? AND password = ?";
                PreparedStatement PPSM = databaseConnection.getConnection().prepareStatement(mysqlUser);
                PPSM.setString(1, username);
                PPSM.setString(2, password);
                ResultSet resultSet = PPSM.executeQuery();

                if (resultSet.next()) {
                    showAlert("Success", "Login successful!");

                } else {
                    showAlert("Error", "Invalid username or password.");
                }
            } catch (SQLException e) {
                showAlert("Database Error", e.getMessage());
            }
        } else {
            showAlert("Error", "Database connection failed.");
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
