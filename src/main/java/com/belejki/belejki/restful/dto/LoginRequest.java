package com.belejki.belejki.restful.dto;

import lombok.Data;

import java.util.Locale;

@Data
public class LoginRequest {
    private String username;
    private String password;
    private String locale;

    // Constructors (optional)
    public LoginRequest() {}

    public LoginRequest(String username, String password, String locale) {
        this.username = username;
        this.password = password;
        this.locale = locale;
    }
}

