package com.belejki.belejki.restful.user.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserRegisterDto {

	private Long id;
	@NotBlank(message = "Username cannot be empty.")
	@Pattern(regexp = "^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$", message = "Username must be in valid email format.")
	private String username;
	private String password;
	@NotBlank(message = "First name cannot be empty.")
	private String firstName;
	@NotBlank(message = "Last name cannot be empty.")
	private String lastName;
	private boolean enabled;
	private String confirmationToken;
	private LocalDateTime tokenExpiry;
}
