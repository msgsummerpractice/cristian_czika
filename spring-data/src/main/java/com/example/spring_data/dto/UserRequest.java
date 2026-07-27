package com.example.spring_data.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "Username cannot be blank.")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters.")
    private String username;

    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Email should be valid.")
    private String email;

    @NotBlank(message = "Password cannot be blank.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    private String password;

    @NotBlank(message = "First name cannot be blank.")
    @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters.")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank.")
    @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters.")
    private String lastName;

}
