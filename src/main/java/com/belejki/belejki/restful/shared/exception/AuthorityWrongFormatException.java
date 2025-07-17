package com.belejki.belejki.restful.shared.exception;

import jakarta.validation.constraints.NotBlank;

public class AuthorityWrongFormatException extends RuntimeException {
    public AuthorityWrongFormatException(@NotBlank String authority) {
        super("Authority wrong format: " + authority);
    }
}
