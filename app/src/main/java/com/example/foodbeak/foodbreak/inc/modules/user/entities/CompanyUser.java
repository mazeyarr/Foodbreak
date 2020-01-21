package com.example.foodbeak.foodbreak.inc.modules.user.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity
public class CompanyUser extends Account {

    @NonNull
    private String location;

    @NonNull
    private Boolean isCompany;

    public CompanyUser(@NonNull String uid, @NonNull String companyName, @NonNull String email, @NonNull String location, @NonNull Boolean isCompany) {
        super(uid, companyName, email);
        this.location = location;
        this.isCompany = isCompany;
    }

    @NonNull
    public String getLocation() {
        return location;
    }

    public void setLocation(@NonNull String location) {
        this.location = location;
    }

    @NonNull
    public Boolean getCompany() {
        return isCompany;
    }

    public void setCompany(@NonNull Boolean company) {
        isCompany = company;
    }
}
