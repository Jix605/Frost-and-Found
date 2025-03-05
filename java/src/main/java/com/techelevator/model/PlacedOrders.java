package com.techelevator.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PlacedOrders {
    private int id;
    @NotNull(message="date and time must be filled for pickup")
    private LocalDateTime pickupAt;
    private String message;
    private int cakeId;
    @NotEmpty(message = "status must be filled")
    private String status;
    private BigDecimal totalPrice;

    public PlacedOrders(int id, LocalDateTime pickupAt, String message, int cakeId, String status, BigDecimal totalPrice) {
        this.id = id;
        this.pickupAt = pickupAt;
        this.cakeId = cakeId;
        this.status = status;
        this.totalPrice = totalPrice;
        this.message = message;
    }

    public PlacedOrders() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getCakeId() {
        return cakeId;
    }

    public void setCakeId(int cakeId) {
        this.cakeId = cakeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
