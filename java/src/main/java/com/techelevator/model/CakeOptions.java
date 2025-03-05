package com.techelevator.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CakeOptions {
    @NotEmpty(message="name must be filled out")
    private String name;
    @NotEmpty(message="category must be filled out")
    private String category;
    private boolean available = true;
    @NotNull(message="price must be given")
    private BigDecimal price;

    public CakeOptions() { // empty constructor
    }

    public CakeOptions(String name, String category, boolean available, BigDecimal price) {
        this.name = name;
        this.category = category;
        this.available = available;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
