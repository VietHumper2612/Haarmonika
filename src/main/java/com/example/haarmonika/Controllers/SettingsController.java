package com.example.haarmonika.Controllers;

import com.example.haarmonika.Database.Repository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SettingsController {

    private final Repository repository = new Repository();

    @FXML
    private TextField databaseUrlField;
    @FXML
    private TextField databaseUserField;
    @FXML
    private TextField databasePasswordField;
    @FXML
    private Button saveSettingsButton;

    @FXML
    public void initialize() {
        // Load current settings if needed
    }

//    @FXML
//    private void handleSaveSettings() {
//        String dbUrl = databaseUrlField.getText();
//        String dbUser = databaseUserField.getText();
//        String dbPassword = databasePasswordField.getText();
//
//        if (dbUrl.isEmpty() || dbUser.isEmpty() || dbPassword.isEmpty()) {
//            showAlert("Please fill in all fields", AlertType.WARNING);
//            return;
//        }
//
//        // Save new settings logic (can implement in the Repository to update configuration)
//        boolean success = repository.updateDatabaseSettings(dbUrl, dbUser, dbPassword);
//
//        if (success) {
//            showAlert("Settings saved successfully", AlertType.INFORMATION);
//        } else {
//            showAlert("Failed to save settings", AlertType.ERROR);
//        }
//    }

    private void showAlert(String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.show();
    }
}
