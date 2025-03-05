package com.example.haarmonika.Database;

import com.example.haarmonika.Objects.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance(); // Get the singleton instance

    public Connection getConnection() {
        return databaseConnection.getConnection();
    }

    public boolean validateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return false;
        }
    }

    public ObservableList<Hairstyle> loadHairstyles() {
        ObservableList<Hairstyle> hairstyles = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            String query = "SELECT * FROM hairstyles";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                String style = resultSet.getString("style");
                int duration = resultSet.getInt("duration");
                hairstyles.add(new Hairstyle(name, price, id, style, duration));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hairstyles;
    }

    public ObservableList<Employee> loadEmployees() {
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            String query = "SELECT * FROM employees";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                employees.add(new Employee(name, id, email, "")); // Assuming no extra info needed for now
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public ObservableList<Customer> loadCustomers() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            String query = "SELECT * FROM customers";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String gender = resultSet.getString("gender");
                customers.add(new Customer(name, id, email, "", gender)); // Assuming no extra info needed for now
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public void editBooking(Booking booking) {
        String sql = "UPDATE bookings SET date = ?, time = ?, hairstyle_id = ?, employee_id = ?, customer_id = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, booking.getDate());
            pstmt.setString(2, booking.getTime());
            pstmt.setInt(3, booking.getHairstyle().getId());
            pstmt.setInt(4, booking.getEmployee().getId());
            pstmt.setInt(5, booking.getCustomer().getId());
            pstmt.setInt(6, booking.getId());
            pstmt.executeUpdate();
            System.out.println("Booking updated successfully");
        } catch (SQLException e) {
            System.out.println("Error updating booking: " + e.getMessage());
        }
    }

    public void deleteBooking(int id) {
        String sql = "DELETE FROM bookings WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Booking deleted successfully");
        } catch (SQLException e) {
            System.out.println("Error deleting booking: " + e.getMessage());
        }
    }

    private boolean isNewCustomer(Customer customer) {
        String query = "SELECT id FROM customers WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, customer.getEmail());
            ResultSet resultSet = statement.executeQuery();
            return !resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean isDateAndTimeAvailable(String date, String time) {
        String query = "SELECT * FROM bookings WHERE date = ? AND time = ?";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, date);
            statement.setString(2, time);
            return !statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void markDateAsUnavailable(String date) {
        String query = "INSERT INTO unavailable_dates (date) VALUES (?)";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, date);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();

        String query = "SELECT bookings.id AS booking_id, bookings.date, bookings.time, " +
                "hairstyles.name AS hairstyle_name, employees.name AS employee_name, " +
                "customers.name AS customer_name " +
                "FROM bookings " +
                "JOIN hairstyles ON bookings.hairstyle_id = hairstyles.id " +
                "JOIN employees ON bookings.employee_id = employees.id " +
                "JOIN customers ON bookings.customer_id = customers.id";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String date = resultSet.getString("date");
                String time = resultSet.getString("time");
                String hairstyleName = resultSet.getString("hairstyle_name");
                String employeeName = resultSet.getString("employee_name");
                String customerName = resultSet.getString("customer_name");


                Booking booking = new Booking();
                booking.setDate(date);
                booking.setTime(time);
                booking.setHairstyle(new Hairstyle(hairstyleName));
                booking.setEmployee(new Employee(employeeName));
                booking.setCustomer(new Customer(customerName, 0, "", "", ""));

                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    public void deleteOldBookings(){
        LocalDate fiveYearsAgo = LocalDate.now().minusYears(5);
        String formattedDate = fiveYearsAgo.toString();

        String sql = "DELETE FROM bookings WHERE date = ?";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, formattedDate);
            pstmt.executeUpdate();
            System.out.println("Old bookings deleted successfully");
        } catch (SQLException e) {
            System.out.println("Error deleting old bookings: " + e.getMessage());
        }
    }
}
