package com.example.haarmonika.Controllers;

import com.example.haarmonika.Database.Repository;
import com.example.haarmonika.Objects.Booking;
import com.example.haarmonika.Objects.Customer;
import com.example.haarmonika.Objects.Employee;
import com.example.haarmonika.Objects.Hairstyle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;

public class BookingController {
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> timeComboBox;
    @FXML
    private ComboBox<Hairstyle> hairstyleComboBox;
    @FXML
    private ComboBox<Employee> employeeComboBox;
    @FXML
    private ComboBox<Customer> customerComboBox;
    @FXML
    private Label statusLabel;

    private final Repository repository = new Repository();

    @FXML
    private void initialize() {
        loadTimeSlots();
        loadHairstyles();
        loadEmployees();
        loadCustomers();
    }

    private void loadTimeSlots() {
        timeComboBox.setItems(FXCollections.observableArrayList("10:00", "11:00", "12:00", "13:00", "14:00"));
    }

    private void loadHairstyles() {
        List<Hairstyle> hairstyles = repository.getHairstyles();
        hairstyleComboBox.setItems(FXCollections.observableArrayList(hairstyles));
    }

    private void loadEmployees() {
        List<Employee> employees = repository.getEmployees();
        employeeComboBox.setItems(FXCollections.observableArrayList(employees));
    }

    private void loadCustomers() {
        List<Customer> customers = repository.getCustomers();
        customerComboBox.setItems(FXCollections.observableArrayList(customers));
    }

    @FXML
    private void handleBooking() {
        try {
            Booking booking = createBookingFromInput();
            boolean success = repository.saveBooking(booking);
            statusLabel.setText(success ? "Booking successful!" : "Booking failed. Date and time already booked.");
        } catch (IllegalArgumentException e) {
            statusLabel.setText("Input error: " + e.getMessage());
        }
    }

    private Booking createBookingFromInput() {
        LocalDate selectedDate = datePicker.getValue();
        String selectedTime = timeComboBox.getValue();
        Hairstyle selectedHairstyle = hairstyleComboBox.getValue();
        Employee selectedEmployee = employeeComboBox.getValue();
        Customer selectedCustomer = customerComboBox.getValue();

        if (selectedDate == null || selectedTime == null || selectedHairstyle == null || selectedEmployee == null || selectedCustomer == null) {
            throw new IllegalArgumentException("All fields must be selected.");
        }

        Booking booking = new Booking();
        booking.setDate(selectedDate.toString());
        booking.setTime(selectedTime);
        booking.setHairstyle(selectedHairstyle);
        booking.setEmployee(selectedEmployee);
        booking.setCustomer(selectedCustomer);

        return booking;
    }
}
