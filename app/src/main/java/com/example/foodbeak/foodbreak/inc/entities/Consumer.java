package com.example.foodbeak.foodbreak.inc.entities;

public class Consumer {
    private String email;
    private String firstname;
    private String lastname;
    private String birthday;

    public Consumer(String email, String firstname, String lastname, String birthday) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
    }

    public Consumer() {
    }

    public static Consumer defConsumer() {
        return new Consumer("", "", "", "");
    }

    public String getFullname() {
        return this.getFirstname() + " " + this.getLastname();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
