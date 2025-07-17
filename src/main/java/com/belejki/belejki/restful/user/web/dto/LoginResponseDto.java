package com.belejki.belejki.restful.user.web.dto;

import com.belejki.belejki.restful.user.domain.User;
import com.belejki.belejki.restful.authority.domain.UserRoles;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class LoginResponseDto {

	private String token;
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private boolean isAdmin;

	public LoginResponseDto(String token, User user) {
		this.id = user.getId();
		this.email = user.getUsername();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.token = token;
		this.isAdmin = user.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.anyMatch(a -> a.equals(UserRoles.ROLE_ADMIN.toString()));
	}
}
