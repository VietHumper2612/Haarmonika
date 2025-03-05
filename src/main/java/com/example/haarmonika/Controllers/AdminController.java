package com.example.haarmonika.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {

    // Handle Manage Users action
    @FXML
    public void handleManageUsers(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/haarmonika/ManageUsers.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Manage Users");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Handle View Reports action
    @FXML
    public void handleViewReports(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/haarmonika/ViewReports.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("View Reports");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Handle Settings action
    @FXML
    public void handleSettings(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/haarmonika/Settings.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Settings");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
