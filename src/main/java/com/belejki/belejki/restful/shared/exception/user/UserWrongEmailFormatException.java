package com.belejki.belejki.restful.shared.exception.user;

public class UserWrongEmailFormatException extends RuntimeException {
    public UserWrongEmailFormatException(String email) {
        super("Incorrect email format: " + email);
    }
}
