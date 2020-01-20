package com.example.foodbeak.foodbreak.inc.modules.user.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    @NonNull
    private String uid;

    @NonNull
    private String fullname;

    @NonNull
    private String email;

    @NonNull
    private String dateOfBirth;

    public User(@NonNull String uid, String fullname, String email, String dateOfBirth) {
        this.uid = uid;
        this.fullname = fullname;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
