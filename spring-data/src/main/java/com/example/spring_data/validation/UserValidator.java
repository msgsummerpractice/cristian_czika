package com.example.spring_data.validation;

import org.springframework.stereotype.Component;

import com.example.spring_data.dto.UserRequest;

@Component
public class UserValidator {

    public boolean isUsernameValid(String username) {
        return username == null || username.isBlank() || username.length() < 3 || username.length() > 20;
    }

    public boolean isEmailValid(String email) {
        return email == null || email.isBlank() || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public boolean isPasswordValid(String password) {
        return password == null || password.isBlank() || password.length() < 8;
    }

    public boolean isFirstNameValid(String firstName) {
        return firstName == null || firstName.isBlank() || firstName.length() < 3 || firstName.length() > 50;
    }

    public boolean isLastNameValid(String lastName) {
        return lastName == null || lastName.isBlank() || lastName.length() < 3 || lastName.length() > 50;
    }

    public boolean validateUserRequest(UserRequest request) {
        return isUsernameValid(request.getUsername())
                && isEmailValid(request.getEmail())
                && isPasswordValid(request.getPassword())
                && isFirstNameValid(request.getFirstName())
                && isLastNameValid(request.getLastName());
    }

}
