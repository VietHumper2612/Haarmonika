package com.example.haarmonika.Controllers;

import com.example.haarmonika.Database.Repository;
import com.example.haarmonika.Objects.Booking;
import com.example.haarmonika.Objects.Customer;
import com.example.haarmonika.Objects.Employee;
import com.example.haarmonika.Objects.Hairstyle;
import javafx.collections.FXCollections;
import com.example.haarmonika.Usecase.Usecase;
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
    private TextField customerNameField;
    @FXML
    private TextField customerEmailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<String> genderComboBox;
    @FXML
    private Label statusLabel;

    private final Repository repository = new Repository();

    @FXML
    private void initialize() {
        loadTimeSlots();
        loadHairstyles();
        loadEmployees();
        loadGenderOptions();
        loadGenderOptions();
    }

    private void loadTimeSlots() {
        timeComboBox.setItems(FXCollections.observableArrayList("10:00", "11:00", "12:00", "13:00", "14:00"));
    }

    private void loadHairstyles() {
        List<Hairstyle> hairstyles = repository.getHairstyles();
        hairstyleComboBox.setItems(FXCollections.observableArrayList(hairstyles));

        hairstyleComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Hairstyle hairstyle, boolean empty) {
                super.updateItem(hairstyle, empty);
                setText(empty || hairstyle == null ? null : hairstyle.getName());
            }
        });
        hairstyleComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Hairstyle hairstyle, boolean empty) {
                super.updateItem(hairstyle, empty);
                setText(empty || hairstyle == null ? null : hairstyle.getName());
            }
        });
    }

    private void loadEmployees() {
        List<Employee> employees = repository.getEmployees();
        employeeComboBox.setItems(FXCollections.observableArrayList(employees));

        employeeComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Employee employee, boolean empty) {
                super.updateItem(employee, empty);
                setText(empty || employee == null ? null : employee.getName());
            }
        });
        employeeComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Employee employee, boolean empty) {
                super.updateItem(employee, empty);
                setText(empty || employee == null ? null : employee.getName());
            }
        });
    }

    private void loadGenderOptions() {
        genderComboBox.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
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

        // Get customer details from text fields
        String customerName = customerNameField.getText();
        String customerEmail = customerEmailField.getText();
        String password = passwordField.getText();
        String gender = genderComboBox.getValue();

        if (selectedDate == null || selectedTime == null || selectedHairstyle == null || selectedEmployee == null || customerName.isEmpty() || customerEmail.isEmpty() || password.isEmpty() || gender == null) {
            throw new IllegalArgumentException("All fields must be selected and customer details must not be empty.");
        }

        // Create a new customer object
        Customer customer = new Customer();
        customer.setName(customerName);
        customer.setEmail(customerEmail);
        customer.setPassword(password); // Assuming your `Customer` class has a setPassword method
        customer.setGender(gender); // Assuming your `Customer` class has a setGender method

        Booking booking = new Booking();
        booking.setDate(selectedDate.toString());
        booking.setTime(selectedTime);
        booking.setHairstyle(selectedHairstyle);
        booking.setEmployee(selectedEmployee);
        booking.setCustomer(customer);
        //kald use case
        Usecase usecase = new Usecase();
        Booking booking =  usecase.createBooking(date, time, hairstyle, employee, customer);

        return booking;
    }
}
