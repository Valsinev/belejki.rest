package com.belejki.belejki.restful.exception;

public class EmptyOrDigitIngridientException extends RuntimeException {
    public EmptyOrDigitIngridientException(Throwable cause) {
        super(cause);
    }

    public EmptyOrDigitIngridientException(String message) {
        super(message);
    }

    public EmptyOrDigitIngridientException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyOrDigitIngridientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
