package com.tawanda.dickensdev.model;

import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class userInfo {
    private String username;
    private String email;
    private String password;
    private UUID userId;
    public userInfo(){

    }
    public userInfo(String username, String email, String password, UUID userId) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
