package com.belejki.belejki.restful.shared.exception;

public class AuthorityAlreadyExistsException extends RuntimeException {
    public AuthorityAlreadyExistsException(String authorityRole) {
        super("There is already authority for this user: " + authorityRole);
    }
}
