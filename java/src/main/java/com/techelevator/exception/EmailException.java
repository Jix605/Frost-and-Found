package com.techelevator.exception;

public class EmailException extends RuntimeException {
    public EmailException() {
        super();
    }
    public EmailException(String message) {
        super(message);
    }
    public EmailException(String message, Exception cause) {
        super(message, cause);
    }
}
