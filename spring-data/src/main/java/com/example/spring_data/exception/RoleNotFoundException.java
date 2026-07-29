package com.example.spring_data.exception;

public class RoleNotFoundException extends BaseException {

    public RoleNotFoundException(String roleName) {
        super("Role " + roleName + " not found!");
    }
}
