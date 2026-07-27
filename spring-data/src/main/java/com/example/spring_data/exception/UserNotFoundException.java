package com.example.spring_data.exception;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException() {
        super("User not found.");
    }
}
