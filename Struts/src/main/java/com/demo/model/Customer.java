package com.demo.model;

import java.io.Serializable;
import java.util.Date;

public class Customer implements Serializable {
    private int id;
    private String customerName;
    private Date birthDate;
    private String gender;
    private String address;

    public Customer() {}

    public Customer(int id, String customerName, Date birthDate, String gender, String address) {
        this.id = id;
        this.customerName = customerName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}