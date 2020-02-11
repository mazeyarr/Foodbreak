package com.example.foodbeak.foodbreak.inc.entities;

import com.example.foodbeak.foodbreak.inc.types.ProductType;

import java.util.UUID;

public class Product {
    private String id;
    private String name;
    private Float price;
    private Integer amount;
    private Boolean isReserved;
    private ProductType productType;
    private Company providedBy;

    public Product(String name, Float price, Boolean isReserved, ProductType productType, Company providedBy) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.price = price;
        this.isReserved = isReserved;
        this.productType = productType;
        this.amount = 1;
        this.providedBy = providedBy;
    }

    public Product(String name, Float price, Integer amount, Boolean isReserved, ProductType productType, Company providedBy) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.price = price;
        this.isReserved = isReserved;
        this.productType = productType;
        this.amount = amount;
        this.providedBy = providedBy;
    }

    public Product() { }

    public static Product defaultProduct(Company company) {
        return new Product(
                "Test Product",
                8.00f,
                false,
                ProductType.FOOD,
                company
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Boolean getReserved() {
        return isReserved;
    }

    public void setReserved(Boolean reserved) {
        isReserved = reserved;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Company getProvidedBy() {
        return providedBy;
    }

    public void setProvidedBy(Company providedBy) {
        this.providedBy = providedBy;
    }
}
