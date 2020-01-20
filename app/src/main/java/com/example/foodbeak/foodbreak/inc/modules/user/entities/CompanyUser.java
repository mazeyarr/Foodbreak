package com.example.foodbeak.foodbreak.inc.modules.user.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity
public class CompanyUser extends Account {
    private static final boolean IS_COMPANY = true;

    @NonNull
    private String location;

    public CompanyUser(@NonNull String uid, @NonNull String fullname, @NonNull String email, @NonNull String location) {
        super(uid, fullname, email);
        this.location = location;
    }

    @NonNull
    public String getLocation() {
        return location;
    }

    public void setLocation(@NonNull String location) {
        this.location = location;
    }
}
