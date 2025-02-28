package com.example.haarmonika.Objects;

public class Booking {

    private int id;
    private String date;
    private String time;
    private Hairstyle hairstyle;
    private Employee employee;
    private Customer customer;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDate() { return date; }
    public void setDate(String date) {
        if (date == null || date.isEmpty()) {
            throw new IllegalArgumentException("Date cannot be empty.");
        }
        this.date = date;
    }
    public String getTime() { return time; }
    public void setTime(String time) {
        if (time == null || time.isEmpty()) {
            throw new IllegalArgumentException("Time cannot be empty.");
        }
        this.time = time;
    }
    public Hairstyle getHairstyle() { return hairstyle; }
    public void setHairstyle(Hairstyle hairstyle) { this.hairstyle = hairstyle; }
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
}
