package com.example.spring_data.controller;

import com.example.spring_data.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.spring_data.security.AuthService;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@RequestBody UserRequest request) {
        UserResponse signedUpUser = authService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(signedUpUser);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signin(@RequestBody SignInRequest request) {
        JwtResponse response = authService.signin(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<SignInResponse> verify(@RequestBody MfaRequest request) {
        SignInResponse response = authService.verifyMfa(request);
        return ResponseEntity.ok(response);
    }

}
