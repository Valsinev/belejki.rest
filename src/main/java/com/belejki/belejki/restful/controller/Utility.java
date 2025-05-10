package com.belejki.belejki.restful.controller;

import org.springframework.security.core.Authentication;

public class Utility {
    public static boolean checkIfOwnerOrAdmin(Authentication authentication, String username) {
        String authenticated = authentication.getName();
        boolean isOwner = authenticated.equals(username);

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        // Only allow deletion if the logged-in user is the same OR the user is an admin
        if (isOwner || isAdmin) {
            return true;
        } else return false;
    }
}
