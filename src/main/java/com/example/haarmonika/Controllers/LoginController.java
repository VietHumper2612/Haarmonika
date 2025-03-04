package com.example.haarmonika.Controllers;

import com.example.haarmonika.Database.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void login(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Username and password must not be empty.");
            return;
        }

        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        if (databaseConnection != null) {
            try {
                String mysqlUser = "SELECT * FROM users WHERE username = ? AND password = ?";
                PreparedStatement PPSM = databaseConnection.getConnection().prepareStatement(mysqlUser);
                PPSM.setString(1, username);
                PPSM.setString(2, password);
                ResultSet resultSet = PPSM.executeQuery();

                if (resultSet.next()) {
                    showAlert("Success", "Login successful!");
                    switchToBookingScreen(event);

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

    private void switchToBookingScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/haarmonika/booking.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Failed to load booking screen.");
            e.printStackTrace();
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
