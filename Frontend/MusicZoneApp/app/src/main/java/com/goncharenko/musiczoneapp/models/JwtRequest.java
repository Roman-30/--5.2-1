package com.goncharenko.musiczoneapp.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JwtRequest {

    @SerializedName("email")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;

    public JwtRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}