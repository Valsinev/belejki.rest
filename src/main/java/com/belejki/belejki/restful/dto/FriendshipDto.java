package com.belejki.belejki.restful.dto;

import com.belejki.belejki.restful.entity.Wish;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FriendshipDto {
    private Long id;
    @NotBlank
    @Email(message = "Invalid format for email address.")
    private String username;
    @NotBlank(message = "First name is required.")
    @Size(min = 2, max = 24, message = "First name must be between 2 and 24 characters.")
    private String firstName;
    @NotBlank(message = "Last name is required.")
    @Size(min = 2, max = 24, message = "Last name must be between 2 and 24 characters.")
    private String lastName;
    @NotBlank
    @Email(message = "Invalid format for email address.")
    private String friendName;


    public FriendshipDto(String username, String friendName) {
        this.friendName = friendName;
        this.username = username;
    }
}
