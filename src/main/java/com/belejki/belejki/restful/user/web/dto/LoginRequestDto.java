package com.belejki.belejki.restful.user.web.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
    private String locale;

    // Constructors (optional)
    public LoginRequestDto() {}

    public LoginRequestDto(String username, String password, String locale) {
        this.username = username;
        this.password = password;
        this.locale = locale;
    }
}

