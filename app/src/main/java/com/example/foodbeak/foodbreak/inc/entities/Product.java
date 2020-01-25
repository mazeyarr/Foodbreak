package com.example.foodbeak.foodbreak.inc.entities;

public class Product {
    private String name;
    private String price;
    private Boolean isReserved;

    public Product(String name, String price, Boolean isReserved) {
        this.name = name;
        this.price = price;
        this.isReserved = isReserved;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Boolean getReserved() {
        return isReserved;
    }

    public void setReserved(Boolean reserved) {
        isReserved = reserved;
    }
}
