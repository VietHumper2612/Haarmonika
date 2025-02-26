package com.example.haarmonika.Objects;

public class Booking {
    int id;
    String date;
    String time;
    Hairstyle hairstyle;
    Employee employee;
    Customer customer;

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getDate() {return date;}
    public void setDate(String date) {this.date = date;}
    public String getTime() {return time;}
    public void setTime(String time) {this.time = time;}
    public Hairstyle getHairstyle() {return hairstyle;}
    public void setHairstyle(Hairstyle hairstyle) {this.hairstyle = hairstyle;}
    public Employee getEmployee() {return employee;}
    public void setEmployee(Employee employee) {this.employee = employee;}
    public Customer getCustomer() {return customer;}
    public void setCustomer(Customer customer) {this.customer = customer;}
}
