package com.belejki.belejki.restful.shared;

import org.springframework.security.core.Authentication;

public interface AuthService {

	boolean checkIfOwnerOrAdminByUsername(Authentication authentication, String username);

	boolean checkIfOwnerOrAdminByUser_Id(Authentication authentication, Long id);

	boolean isAdmin(Authentication authentication);

}
