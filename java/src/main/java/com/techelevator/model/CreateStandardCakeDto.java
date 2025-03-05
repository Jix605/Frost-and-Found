package com.techelevator.model;

import jakarta.validation.constraints.NotNull;

public class CreateStandardCakeDto {
    @NotNull(message="cake must be provided")
    private CakeDetailsDto cakeToAdd;
    @NotNull(message="standard cake must be provided")
    private StandardCakes cakeBlueprint;

    public CreateStandardCakeDto(CakeDetailsDto cakeToAdd, StandardCakes cakeBlueprint) {
        this.cakeToAdd = cakeToAdd;
        this.cakeBlueprint = cakeBlueprint;
    }

    public CreateStandardCakeDto() {}

    public CakeDetailsDto getCakeToAdd() {
        return cakeToAdd;
    }

    public void setCakeToAdd(CakeDetailsDto cakeToAdd) {
        this.cakeToAdd = cakeToAdd;
    }

    public StandardCakes getCakeBlueprint() {
        return cakeBlueprint;
    }

    public void setCakeBlueprint(StandardCakes cakeBlueprint) {
        this.cakeBlueprint = cakeBlueprint;
    }
}
