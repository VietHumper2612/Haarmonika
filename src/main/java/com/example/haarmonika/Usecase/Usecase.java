package com.example.haarmonika.Usecase;

import com.example.haarmonika.Objects.Booking;
import com.example.haarmonika.Objects.Customer;
import com.example.haarmonika.Objects.Employee;
import com.example.haarmonika.Objects.Hairstyle;

import java.time.LocalDate;

public class Usecase {

    public Booking createBooking(LocalDate date, String time, Hairstyle hairstyle, Employee employee, Customer customer) {
        Booking booking = new Booking();
        booking.setDate(String.valueOf(date));
        booking.setTime(time);
        booking.setHairstyle(new Hairstyle(hairstyle));
        booking.setEmployee(new Employee(employee));
        booking.setCustomer(new Customer(customer));

        return booking;
    }
}
