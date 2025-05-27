package com.pigierbackend.dto;  // Ou com.pigierbackend.model selon votre structure

import lombok.Builder;

@Builder
public class AuthenticationResponse {
    private  String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    // Getter
    public String getToken() {
        return token;
    }

    // Optionnel : Setter si nécessaire
    public void setToken(String token) {
        this.token = token;
    }
}