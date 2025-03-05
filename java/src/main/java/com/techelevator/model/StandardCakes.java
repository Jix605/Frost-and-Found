package com.techelevator.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class StandardCakes {
    private int id;
    private int cakeId;
    @NotEmpty(message="name must be provided")
    private String name;
    @NotNull(message="price must be given")
    private BigDecimal price;
    @NotEmpty(message="description must be provided")
    private String description;
    private boolean available = true;

    public StandardCakes(int id, int cakeId, String name, BigDecimal price, String description, boolean available) {
        this.id = id;
        this.cakeId = cakeId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.available = available;
    }

    public StandardCakes() {
    }

    public int getCakeId() {
        return cakeId;
    }

    public void setCakeId(int cakeId) {
        this.cakeId = cakeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
