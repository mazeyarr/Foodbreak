package com.example.foodbeak.foodbreak.inc.modules.product.entities;

import com.example.foodbeak.foodbreak.inc.modules.user.entities.CompanyUser;

public class Product {
    private String name;
    private String price;
    private Boolean isReserved;
    private CompanyUser company;

    public Product(String name, String price, Boolean isReserved, CompanyUser company) {
        this.name = name;
        this.price = price;
        this.isReserved = isReserved;
        this.company = company;
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

    public CompanyUser getCompany() {
        return company;
    }

    public void setCompany(CompanyUser company) {
        this.company = company;
    }
}
