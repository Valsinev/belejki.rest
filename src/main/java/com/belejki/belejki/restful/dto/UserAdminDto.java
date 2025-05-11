package com.belejki.belejki.restful.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class UserAdminDto {

    private Long id;
    @Email(message = "Invalid format for email address.")
    @NotBlank(message = "Username is required.")
    private String username;
    @NotBlank(message = "First name is required.")
    private String firstName;
    @NotBlank(message = "Last name is required.")
    private String lastName;
    private boolean enabled;
    private LocalDate lastLogin;
    private boolean setForDeletion;
    private List<AuthorityAdminDto> authorities;

}
