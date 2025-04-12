package com.belejki.belejki.restful.exception;

public class NullIngridientException extends RuntimeException {

    public NullIngridientException(Throwable cause) {
        super(cause);
    }

    public NullIngridientException(String message) {
        super(message);
    }

    public NullIngridientException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullIngridientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
