package com.techelevator.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ExtrasDto {
    @NotEmpty(message="name must be filled")
    private String name;

    public ExtrasDto(String name) {
        this.name = name;
    }

    public ExtrasDto() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
