package com.belejki.belejki.restful.exception;

public class UserWrongEmailFormatException extends RuntimeException {
    public UserWrongEmailFormatException(String email) {
        super("Incorrect email format: " + email);
    }
}
