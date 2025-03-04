package com.example.haarmonika.Database;

import com.example.haarmonika.Objects.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Repository {
    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance(); // Get the singleton instance

    public Connection getConnection() {
        return databaseConnection.getConnection();
    }

    public boolean validateUser(String username, String password) {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return false;
        }
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

    public boolean saveBooking(Booking booking) {
        if (isDateAndTimeAvailable(booking.getDate(), booking.getTime())) {
            String query = "INSERT INTO bookings (date, time, hairstyle_id, employee_id, customer_id) VALUES (?, ?, ?, ?, ?)";
            try (Connection conn = getConnection();
                 PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, booking.getDate());
                statement.setString(2, booking.getTime());
                statement.setString(3, booking.getHairstyle().getName());
                statement.setString(4, booking.getEmployee().getName());
                statement.setString(5, booking.getCustomer().getName());
                statement.executeUpdate();
                markDateAsUnavailable(booking.getDate());
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean isDateAndTimeAvailable(String date, String time) {
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

    private void markDateAsUnavailable(String date) {
        String query = "INSERT INTO unavailable_dates (date) VALUES (?)";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, date);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Hairstyle> getHairstyles() {
        List<Hairstyle> hairstyles = new ArrayList<>();
        String query = "SELECT id, name, price, style, duration FROM hairstyles";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                hairstyles.add(new Hairstyle(
                        rs.getInt("price"),
                        rs.getInt("id"),
                        rs.getString("style"),
                        rs.getInt("duration")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hairstyles;
    }

    public List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT id, name, email, password FROM employees";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                employees.add(new Employee(
                        rs.getString("name"),
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }

    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT id, name, email, password, gender FROM customers";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                customers.add(new Customer(
                        rs.getString("name"),
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("gender")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }

    public void deleteOldBookings(){
        LocalDate fiveYearsAgo = LocalDate.now().minusYears(5);
        String formattedDate = fiveYearsAgo.toString();

        String sql = "DELETE FROM bookings WHERE date = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, formattedDate);
            pstmt.executeUpdate();
            System.out.println("Old bookings deleted successfully");
        } catch (SQLException e) {
            System.out.println("Error deleting old bookings: " + e.getMessage());
        }
    }
}
