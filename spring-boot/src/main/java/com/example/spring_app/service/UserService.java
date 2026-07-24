package com.example.spring_app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.spring_app.model.User;
import com.example.spring_app.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
