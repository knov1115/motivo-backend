package com.example.fitnessapp.controllers.auth;

import org.springframework.security.core.userdetails.UserDetails;

public class AuthResponse {
    private String token;
    private UserDetails userDetails;

    public AuthResponse(String token, UserDetails userDetails) {
        this.token = token;
        this.userDetails = userDetails;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public UserDetails getUserDetails() { return userDetails; }
    public void setUserDetails(UserDetails userDetails) { this.userDetails = userDetails; }
}
