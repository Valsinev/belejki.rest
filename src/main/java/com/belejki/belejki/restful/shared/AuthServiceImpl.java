package com.belejki.belejki.restful.shared;

import com.belejki.belejki.restful.authority.domain.UserRoles;
import com.belejki.belejki.restful.user.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceImpl  implements AuthService{

    public boolean checkIfOwnerOrAdminByUsername(Authentication authentication, String username) {
        String authenticated = authentication.getName();
        boolean isOwner = authenticated.equals(username);

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals(UserRoles.ROLE_ADMIN.toString()));
        // Only allow deletion if the logged-in user is the same OR the user is an admin
        if (isOwner || isAdmin) {
            return true;
        } else return false;
    }

    public boolean checkIfOwnerOrAdminByUser_Id(Authentication authentication, Long id) {
        User authenticated = (User) authentication.getPrincipal();
        boolean isOwner = authenticated.getId().equals(id);

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals(UserRoles.ROLE_ADMIN.toString()));
        // Only allow deletion if the logged-in user is the same OR the user is an admin
        if (isOwner || isAdmin) {
            return true;
        } else return false;
    }


    public boolean isAdmin(Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals(UserRoles.ROLE_ADMIN.name()));
        return isAdmin;
    }
}
