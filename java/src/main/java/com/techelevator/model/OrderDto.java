package com.techelevator.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class OrderDto {
    @NotNull(message="pickup date and time must be included")
    private LocalDateTime pickupAt;
    private String message;
    @NotNull(message="cake details must be provided")
    private CakeDetailsDto cakeDetails;
    @NotNull(message="customer information must be provided")
    private Customer customer;

    public OrderDto(LocalDateTime pickupAt, String message, CakeDetailsDto cakeDetails, Customer customer) {
        this.pickupAt = pickupAt;
        this.cakeDetails = cakeDetails;
        this.customer = customer;
        this.message = message;
    }

    public OrderDto() {}

    public LocalDateTime getPickupAt() {
        return pickupAt;
    }

    public void setPickupAt(LocalDateTime pickupAt) {
        this.pickupAt = pickupAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CakeDetailsDto getCakeDetails() {
        return cakeDetails;
    }

    public void setCakeDetails(CakeDetailsDto cakeDetails) {
        this.cakeDetails = cakeDetails;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}