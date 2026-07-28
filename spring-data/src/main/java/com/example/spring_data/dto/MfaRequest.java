package com.example.spring_data.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MfaRequest {

    @NotNull(message = "Username cannot be blank.")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotNull(message = "MFA Code cannot be blank.")
    @Size(min = 6, max = 6, message = "MFA Code must be 6 characters long.")
    private String code;
    
}
