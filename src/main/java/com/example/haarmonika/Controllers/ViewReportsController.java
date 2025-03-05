package com.example.haarmonika.Controllers;

import com.example.haarmonika.Database.Repository;
import com.example.haarmonika.Objects.Booking;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.List;

public class ViewReportsController {

    private final Repository repository = new Repository();

    @FXML
    private ListView<String> reportListView;

    @FXML
    public void initialize() {
        loadReports();
    }

    private void loadReports() {
        List<Booking> bookings = repository.getAllBookings();

        if (bookings.isEmpty()) {
            showAlert("No bookings found", AlertType.WARNING);
            return;
        }

        // Populate the list view with booking data or other reports
        for (Booking booking : bookings) {
            String report = String.format("Booking ID: %d, Date: %s, Time: %s, Hairstyle: %s, Employee: %s, Customer: %s",
                    booking.getId(), booking.getDate(), booking.getTime(), booking.getHairstyle().getName(),
                    booking.getEmployee().getName(), booking.getCustomer().getName());
            reportListView.getItems().add(report);
        }
    }

    private void showAlert(String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.show();
    }
}