package com.techelevator.model;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class CakeDetailsDto {
    private int id;
    @NotEmpty(message="style must be given")
    private String style;
    @NotEmpty(message="size must be given")
    private String size;
    @NotEmpty(message="flavor must be given")
    private String flavor;
    private String frosting;
    private String filling;
    private List<ExtrasDto> extras;

    public CakeDetailsDto(int id, String style, String size, String flavor, String frosting, String filling, List<ExtrasDto> extras) {
        this.id = id;
        this.style = style;
        this.size = size;
        this.flavor = flavor;
        this.frosting = frosting;
        this.filling = filling;
        this.extras = extras;
    }

    public CakeDetailsDto() {}

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getFrosting() {
        return frosting;
    }

    public void setFrosting(String frosting) {
        this.frosting = frosting;
    }

    public String getFilling() {
        return filling;
    }

    public void setFilling(String filling) {
        this.filling = filling;
    }

    public List<ExtrasDto> getExtras() {
        return extras;
    }

    public void setExtras(List<ExtrasDto> extras) {
        this.extras = extras;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String strToReturn = "  style: " + style + '\n'
                + "  size: " + size + '\n'
                + "  flavor: " + flavor + '\n'
                + "  frosting: " + frosting + '\n'
                + "  filling: " + filling;

        for (ExtrasDto extra : extras){
            strToReturn += "\n  extra: " + extra.getName();
        }

        return strToReturn;
    }
}
