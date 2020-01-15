package com.example.foodbeak.foodbreak.inc.modules.user.entities.User;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class UserEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "user_id")
    private Long id;

    @NonNull
    @ColumnInfo(name = "firstname")
    private String firstname;

    @NonNull
    @ColumnInfo(name = "lastname")
    private String lastname;

    @NonNull
    @ColumnInfo(name = "email")
    private String email;

    @NonNull
    @ColumnInfo(name = "birthdate")
    private String birtdate;

    @NonNull
    @ColumnInfo(name = "password")
    private String password;

    public UserEntity(
            @NonNull Long id,
            @NonNull String firstname,
            @NonNull String lastname,
            @NonNull String email,
            @NonNull String birtdate,
            @NonNull String password
    ) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.birtdate = birtdate;
        this.password = password;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    @NonNull
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(@NonNull String firstname) {
        this.firstname = firstname;
    }

    @NonNull
    public String getLastname() {
        return lastname;
    }

    public void setLastname(@NonNull String lastname) {
        this.lastname = lastname;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getBirtdate() {
        return birtdate;
    }

    public void setBirtdate(@NonNull String birtdate) {
        this.birtdate = birtdate;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }
}
