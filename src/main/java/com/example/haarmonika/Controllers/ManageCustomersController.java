package com.example.haarmonika.Controllers;

import com.example.haarmonika.Database.Repository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ManageCustomersController {

    private final Repository repository = new Repository();

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button updateButton;

    @FXML
    public void initialize() {
        // Initialize the controller if needed
    }

    @FXML
    private void handleAddCustomer() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            showAlert("Please fill in all fields", AlertType.WARNING);
            return;
        }

        // Add customer logic (you can implement your add logic in the Repository)
        boolean success = repository.addCustomer(name, email, phone);

        if (success) {
            showAlert("Customer added successfully", AlertType.INFORMATION);
        } else {
            showAlert("Failed to add customer", AlertType.ERROR);
        }
    }

    @FXML
    private void handleDeleteCustomer() {
        String email = emailField.getText();

        if (email.isEmpty()) {
            showAlert("Please provide an email to delete", AlertType.WARNING);
            return;
        }

        boolean success = repository.deleteCustomer(email);

        if (success) {
            showAlert("Customer deleted successfully", AlertType.INFORMATION);
        } else {
            showAlert("Failed to delete customer", AlertType.ERROR);
        }
    }

    @FXML
    private void handleUpdateCustomer() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            showAlert("Please fill in all fields", AlertType.WARNING);
            return;
        }

        // Update customer logic
        boolean success = repository.editCustomer(name, email, phone);

        if (success) {
            showAlert("Customer updated successfully", AlertType.INFORMATION);
        } else {
            showAlert("Failed to update customer", AlertType.ERROR);
        }
    }

    private void showAlert(String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.show();
    }
}