package com.example.haarmonika.Controllers;

import com.example.haarmonika.Database.Repository;
import com.example.haarmonika.Objects.Booking;
import com.example.haarmonika.Objects.Customer;
import com.example.haarmonika.Objects.Employee;
import com.example.haarmonika.Objects.Hairstyle;
import com.example.haarmonika.Usecase.Usecase;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class BookingController {
    @FXML
    private TextField dateField;
    @FXML
    private TextField timeField;
    @FXML
    private TextField hairstyleField;
    @FXML
    private TextField employeeField;
    @FXML
    private TextField customerField;
    @FXML
    private Label statusLabel;

    private Repository repository = new Repository();

    @FXML
    private void handleBooking() {
        try {
            Booking booking = createBookingFromInput();
            boolean success = repository.saveBooking(booking);
            statusLabel.setText(success ? "Booking successful!" : "Booking failed. Date and time is already booked.");
        } catch (IllegalArgumentException e) {
            statusLabel.setText("Input error: " + e.getMessage());
        }

    }


    private Booking createBookingFromInput() {
        String date = dateField.getText();
        String time = timeField.getText();
        String hairstyle = hairstyleField.getText();
        String employee = employeeField.getText();
        String customer = customerField.getText();

        // Enkel validering
        if (date.isEmpty() || time.isEmpty() || hairstyle.isEmpty() || employee.isEmpty() || customer.isEmpty()) {
            throw new IllegalArgumentException("All fields must be filled.");
        }

        //kald use case
        Usecase usecase = new Usecase();
        Booking booking =  usecase.createBooking(date, time, hairstyle, employee, customer);

        return booking;
    }
}