package com.belejki.belejki.restful.exception;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
