package com.example.haarmonika.Usecase;

import com.example.haarmonika.Objects.Booking;
import com.example.haarmonika.Objects.Customer;
import com.example.haarmonika.Objects.Employee;
import com.example.haarmonika.Objects.Hairstyle;

public class Usecase {

    public Booking createBooking(String date, String time, String hairstyle, String employee, String customer) {
        Booking booking = new Booking();
        booking.setDate(date);
        booking.setTime(time);
        booking.setHairstyle(new Hairstyle(hairstyle));
        booking.setEmployee(new Employee(employee));
        booking.setCustomer(new Customer(customer));

        return booking;
    }
}
