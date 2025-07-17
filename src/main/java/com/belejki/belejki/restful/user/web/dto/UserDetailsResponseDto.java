package com.belejki.belejki.restful.user.web.dto;

import com.belejki.belejki.restful.authority.web.dto.AuthorityDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class UserDetailsResponseDto {

	private Long id;
	private String username;
	private String firstName;
	private String lastName;
	private boolean enabled;
	private LocalDate lastLogin;
	private boolean setForDeletion;
	private String locale;
	private Set<AuthorityDto> authorities;

}
