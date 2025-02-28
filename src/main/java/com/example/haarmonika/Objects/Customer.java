package com.example.haarmonika.Objects;

import com.example.haarmonika.Interfaces.Person;

public class Customer implements Person {

        private String name;
        private int id;
        private String email;
        private String password;
        private String gender;

        public Customer(String name, int id, String email, String password, String gender) {
            this.name = name;
            this.id = id;
            this.email = email;
            this.password = password;
            this.gender = gender;
        }

    public Customer(String customer) {
    }

    public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getGender() { return gender; }
        public void setGender(String gender) { this.gender = gender; }

        @Override
        public String toString() {
            return "Customer{" +
                    "name='" + name + '\'' +
                    ", id=" + id +
                    ", email='" + email + '\'' +
                    ", gender='" + gender + '\'' +
                    '}';
        }

    }
