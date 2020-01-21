package com.example.foodbeak.foodbreak.inc.modules.user.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Account {
    @PrimaryKey
    @NonNull
    private String uid;

    @NonNull
    private String name;

    @NonNull
    private String email;

    public Account(@NonNull String uid, @NonNull String name, @NonNull String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
