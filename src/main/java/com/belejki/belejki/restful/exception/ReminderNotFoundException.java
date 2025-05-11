package com.belejki.belejki.restful.exception;

public class ReminderNotFoundException extends RuntimeException{
    public ReminderNotFoundException(String message) {
        super(message);
    }
}
