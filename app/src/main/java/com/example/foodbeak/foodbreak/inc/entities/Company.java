package com.example.foodbeak.foodbreak.inc.entities;

public class Company {
    private String email;
    private String name;
    private String location;
    private Boolean isCompany;

    public Company(String email, String name, String location) {
        this.email = email;
        this.name = name;
        this.location = location;
        this.isCompany = true;
    }

    public Company() {
    }

    public static Company defCompany() {
        return new Company("", "", "");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getCompany() {
        return isCompany;
    }

    public void setCompany(Boolean company) {
        isCompany = company;
    }
}
