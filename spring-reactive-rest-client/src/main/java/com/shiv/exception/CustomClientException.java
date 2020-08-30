package com.shiv.exception;

public class CustomClientException  extends RuntimeException {

    private int statusCode;

    public CustomClientException(final String message, final int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
