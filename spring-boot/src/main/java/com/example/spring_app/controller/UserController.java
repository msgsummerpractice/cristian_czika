package com.example.spring_app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_app.model.User;
import com.example.spring_app.service.UserService;

import jakarta.validation.constraints.Size;

@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(
            @RequestParam(required = false) @Size(min = 3, max = 15, message = "Role must be between 3 and 15 characters") String role
    ) {
        logger.info("Fetching All Users");

        HttpHeaders headers = new HttpHeaders();

        headers.add(
                "X-Total-Count", "3");

        return new ResponseEntity<>(userService.getAllUsers(), headers, HttpStatus.OK);
    }

}
