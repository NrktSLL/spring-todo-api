package com.nrkt.springboottodoapi.exception;

public class MailAlreadyExistException extends RuntimeException {
    public MailAlreadyExistException(String message) {
        super(message);
    }
}
