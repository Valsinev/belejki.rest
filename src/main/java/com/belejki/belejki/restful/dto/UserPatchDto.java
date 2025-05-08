package com.belejki.belejki.restful.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserPatchDto {
    @Email(message = "Invalid email format.")
    private String username;

    @Size(min = 2, max = 24, message = "First name must be between 2 and 24 characters.")
    private String firstName;

    @Size(min = 2, max = 24, message = "Last name must be between 2 and 24 characters.")
    private String lastName;

    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters.")
    private String password;
}

