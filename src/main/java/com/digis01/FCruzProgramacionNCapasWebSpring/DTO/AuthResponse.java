package com.digis01.FCruzProgramacionNCapasWebSpring.DTO;

public class AuthResponse {
    
    private String token;
    
    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
    
    
    
}
