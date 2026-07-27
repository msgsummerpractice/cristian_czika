package com.example.spring_data.exception;

public class EmailAlreadyExistsException extends BaseException {

    public EmailAlreadyExistsException(String email) {
        super("Email " + email + " is already in use.");
    }
}
