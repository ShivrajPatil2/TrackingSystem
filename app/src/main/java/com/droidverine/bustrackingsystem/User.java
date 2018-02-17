package com.droidverine.bustrackingsystem;

/**
 * Created by DELL on 17-02-2018.
 */

public class User {
    String email,status;

    public User() {
    }

    public User(String email, String status) {
        this.email = email;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
