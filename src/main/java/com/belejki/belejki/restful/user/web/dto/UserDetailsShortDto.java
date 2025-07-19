package com.belejki.belejki.restful.user.web.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDetailsShortDto {

	private Long id;
	private String username;
	private String firstName;
	private String lastName;
	private List<String> authorities;
	private boolean isAdmin;
}
