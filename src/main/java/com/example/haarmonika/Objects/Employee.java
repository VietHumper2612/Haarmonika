package com.example.haarmonika.Objects;

import com.example.haarmonika.Interfaces.Person;

public class Employee implements Person {
    String name;
    int id;
    String email;
    String password;

    public Employee(String name, int id, String email, String password) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Employee(String name) {
        this.name = name;
    }

    public String getName() {return name;}
    public Employee setName(String name) {this.name = name;return this;}
    public int getId() {return id;}
    public Employee setId(int id) {this.id = id;return this;}
    public String email() {return email;}
    public Employee setEmail(String email) {this.email = email;return this;}
    public String password() {return password;}
    public Employee setPassword(String password) {this.password = password;return this;}
}
