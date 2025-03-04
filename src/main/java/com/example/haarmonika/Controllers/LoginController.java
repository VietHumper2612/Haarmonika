package com.example.haarmonika.Controllers;

import com.example.haarmonika.Database.Repository;
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

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private final Repository repository = new Repository();  // Moved this outside the method

    @FXML
    private void login(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (loginValidation(username, password)) {
            showAlert("Success", "Login successful!");
            switchToBookingScreen(event);
        }
    }

    private boolean loginValidation(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Username and password must not be empty.");
            return false;
        }
        return repository.validateUser(username, password);
    }

    private void switchToBookingScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/haarmonika/booking.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Booking");
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
