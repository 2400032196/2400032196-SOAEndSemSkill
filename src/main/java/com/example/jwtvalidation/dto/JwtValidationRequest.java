package com.example.jwtvalidation.dto;

public class JwtValidationRequest {
    private String token;

    public JwtValidationRequest() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
