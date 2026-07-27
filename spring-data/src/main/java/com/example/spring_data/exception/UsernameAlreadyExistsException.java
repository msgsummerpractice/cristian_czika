package com.example.spring_data.exception;

public class UsernameAlreadyExistsException extends BaseException {

    public UsernameAlreadyExistsException(String username) {
        super("Username " + username + " is already in use.");
    }
}
