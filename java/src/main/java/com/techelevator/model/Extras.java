package com.techelevator.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class Extras {
    @NotEmpty(message="name must be filled")
    private String name;
    @NotNull(message="extra must have a price")
    private BigDecimal price;
    private int cakeId;

    public Extras(String name, BigDecimal price, int cakeId) {
        this.name = name;
        this.price = price;
        this.cakeId = cakeId;
    }

    public Extras() {
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

    public int getCakeId() {
        return cakeId;
    }

    public void setCakeId(int cakeId) {
        this.cakeId = cakeId;
    }
}
