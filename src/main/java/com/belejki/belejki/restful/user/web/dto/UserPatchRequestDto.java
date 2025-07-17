package com.belejki.belejki.restful.user.web.dto;

import lombok.Data;

@Data
public class UserPatchRequestDto {
	private final String username;
	private final String firstName;
	private final String lastName;
	private final String password;
}
