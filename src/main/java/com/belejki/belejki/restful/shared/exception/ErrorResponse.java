package com.belejki.belejki.restful.shared.exception;

import lombok.Data;


@Data
public class ErrorResponse {
    private int status;
    private String message;
    private long timeStamp;

    public ErrorResponse() {
    }

    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
        this.timeStamp = System.currentTimeMillis();
    }
}
