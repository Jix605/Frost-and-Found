package com.techelevator.model;

import jakarta.validation.constraints.NotEmpty;

public class Customer {
    private int id;
    @NotEmpty(message="name must be given")
    private String name;
    @NotEmpty(message="phone number must be given")
    private String phoneNumber;
    @NotEmpty(message="email must be given")
    private String email;
    private int placedOrderId;

    public Customer(int id, String name, String phoneNumber, String email, int placedOrderId) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.placedOrderId = placedOrderId;
    }

    public Customer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPlacedOrderId() {
        return placedOrderId;
    }

    public void setPlacedOrderId(int placedOrderId) {
        this.placedOrderId = placedOrderId;
    }
}
