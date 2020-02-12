package com.example.foodbeak.foodbreak.inc.entities;

import java.util.ArrayList;
import java.util.Random;

public class Order {
    private Integer orderNumber;
    private Company company;
    private ArrayList<Product> products;
    private Boolean isDone;

    public Order(Company company, ArrayList<Product> products) {
        Random r = new Random();
        this.orderNumber = r.nextInt(100);
        this.company = company;
        this.products = products;
        this.isDone = false;
    }

    public Order() {
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }
}
