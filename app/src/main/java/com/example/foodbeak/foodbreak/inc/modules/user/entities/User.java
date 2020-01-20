package com.example.foodbeak.foodbreak.inc.modules.user.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity
public class User extends Account {
    @NonNull
    private String dateOfBirth;

    public User(@NonNull String uid, @NonNull String fullname, @NonNull String email, @NonNull String dateOfBirth) {
        super(uid, fullname, email);
        this.dateOfBirth = dateOfBirth;
    }

    @NonNull
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(@NonNull String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
