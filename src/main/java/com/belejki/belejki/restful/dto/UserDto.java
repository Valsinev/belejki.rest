package com.belejki.belejki.restful.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserDto {
    @Email(message = "Invalid format for email address.")
    @NotBlank(message = "Username is required.")
    private String username;
    @NotBlank(message = "First name is required.")
    private String firstName;
    @NotBlank(message = "Last name is required.")
    private String lastName;
    private boolean enabled;
    private LocalDate lastLogin;
    @NotBlank(message = "Password is required.")
    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters.")
    private String password;



    public UserDto(String username) {
        this.username = username;
    }

    public UserDto(String username, String firstName, String lastName, boolean enabled) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
    }
}
