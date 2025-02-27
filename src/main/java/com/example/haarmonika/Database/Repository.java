package com.example.haarmonika.Database;

import com.example.haarmonika.Objects.Booking;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.haarmonika.Database.DatabaseConnection;
import com.example.haarmonika.Objects.Booking;
import java.sql.*;

public class Repository {

    private static final Connection conn = DatabaseConnection.getInstance().getConnection();

    public static void editBooking(Booking booking) {
        String sql = "UPDATE Booking SET date = ?, time = ?, hairstyle_id = ?, employee_id = ?, customer_id = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
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

    public static void deleteBooking(int id) {
        String sql = "DELETE FROM Booking WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Booking deleted successfully");
        } catch (SQLException e) {
            System.out.println("Error deleting booking: " + e.getMessage());
        }
    }

    public boolean saveBooking(Booking booking) {
        if (isDateAndTimeAvailable(booking.getDate(), booking.getTime())) {
            try {
                String query = "INSERT INTO bookings (date, time, hairstyle, employee, customer) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(query);
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
        try {
            String query = "SELECT * FROM bookings WHERE date = ? AND time = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, date);
            statement.setString(2, time);
            return !statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void markDateAsUnavailable(String date) {
        try {
            String query = "INSERT INTO unavailable_dates (date) VALUES (?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, date);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}