package com.example.spring_data.exception;

public class InvalidUserRequestException extends BaseException {

    public InvalidUserRequestException() {
        super("Invalid user request.");
    }
}
