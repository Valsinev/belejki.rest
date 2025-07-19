package com.belejki.belejki.restful.user.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserPatchRequestDto {
	@NotNull
	private final Long id;
	private final String username;
	private final String firstName;
	private final String lastName;
	private final String password;
}
