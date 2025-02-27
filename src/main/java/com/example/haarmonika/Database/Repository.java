package com.example.haarmonika.Database;

import com.example.haarmonika.Objects.Booking;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Repository {

    private Connection connection;

    public Repository() {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        connection = databaseConnection.getConnection();
    }

    public boolean saveBooking(Booking booking) {
        if (isDateAvailable(booking.getDate(), booking.getTime())) {
            try {
                String query = "INSERT INTO bookings (date, time, hairstyle, employee, customer) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
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

    private boolean isDateAvailable(String date, String time) {
        try {
            String query = "SELECT * FROM bookings WHERE date = ? AND time = ?";
            PreparedStatement statement = connection.prepareStatement(query);
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
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, date);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}