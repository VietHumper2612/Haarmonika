package com.example.haarmonika.Controllers;

import com.example.haarmonika.Database.Repository;
import com.example.haarmonika.Database.DatabaseConnection;
import com.example.haarmonika.Objects.Booking;
import com.example.haarmonika.Objects.Customer;
import com.example.haarmonika.Objects.Employee;
import com.example.haarmonika.Objects.Hairstyle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    private Button bookButton;

    private ObservableList<String> availableTimes = FXCollections.observableArrayList("10:00", "11:00", "12:00", "13:00");

    private final Repository repository = new Repository();

    @FXML
    public void initialize() {
        timeComboBox.setItems(availableTimes);
        hairstyleComboBox.setItems(repository.loadHairstyles());
        employeeComboBox.setItems(repository.loadEmployees());
        customerComboBox.setItems(repository.loadCustomers());
    }

    @FXML
    private void handleBooking(ActionEvent event) {

        if (datePicker.getValue() == null || timeComboBox.getValue() == null ||
                hairstyleComboBox.getValue() == null || employeeComboBox.getValue() == null || customerComboBox.getValue() == null) {
            showAlert("Error", "Please fill in all the fields.");
            return;
        }
        String date = datePicker.getValue().toString();
        String time = timeComboBox.getValue();

        if (!repository.isDateAndTimeAvailable(date, time)) {
            showAlert("Error", "The selected date and time are already booked. Please choose another.");
            return;  // Don't proceed with booking if it's unavailable
        }

        Hairstyle hairstyle = hairstyleComboBox.getValue();
        Employee employee = employeeComboBox.getValue();
        Customer customer = customerComboBox.getValue();

        Booking booking = new Booking();
        booking.setDate(date);
        booking.setTime(time);
        booking.setHairstyle(hairstyle);
        booking.setEmployee(employee);
        booking.setCustomer(customer);

        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            String query = "INSERT INTO bookings (date, time, hairstyle_id, employee_id, customer_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, date);
            statement.setString(2, time);
            statement.setInt(3, hairstyle.getId());
            statement.setInt(4, employee.getId());
            statement.setInt(5, customer.getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Success", "Booking successfully created!");
                repository.markDateAsUnavailable(date);
            } else {
                showAlert("Error", "Failed to create booking.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Database error: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void showCurrentBookings() {
        List<Booking> bookings = repository.getAllBookings();

        StringBuilder bookingsText = new StringBuilder();
        bookingsText.append("Current Bookings:\n\n");

        for (Booking booking : bookings) {
            bookingsText.append("Date: ").append(booking.getDate())
                    .append(", Time: ").append(booking.getTime())
                    .append("\nHairstyle: ").append(booking.getHairstyle().getName())
                    .append(", Employee: ").append(booking.getEmployee().getName())
                    .append(", Customer: ").append(booking.getCustomer().getName())
                    .append("\n\n");
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Current Bookings");
        alert.setHeaderText(null);
        alert.setContentText(bookingsText.toString());

        alert.showAndWait();
}}
