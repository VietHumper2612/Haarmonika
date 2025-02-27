package com.example.haarmonika.Controllers;

import com.example.haarmonika.Database.Repository;
import com.example.haarmonika.Objects.Booking;
import com.example.haarmonika.Objects.Customer;
import com.example.haarmonika.Objects.Employee;
import com.example.haarmonika.Objects.Hairstyle;
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
        String date = dateField.getText();
        String time = timeField.getText();
        String hairstyle = hairstyleField.getText();
        String employee = employeeField.getText();
        String customer = customerField.getText();

        Booking booking = new Booking();
        booking.setDate(date);
        booking.setTime(time);
        booking.setHairstyle(new Hairstyle(hairstyle));
        booking.setEmployee(new Employee(employee));
        booking.setCustomer(new Customer(customer));

        boolean success = repository.saveBooking(booking);
        if (success) {
            statusLabel.setText("Booking successful!");
        } else {
            statusLabel.setText("Booking failed. Date and time is already booked.");
        }
    }
}