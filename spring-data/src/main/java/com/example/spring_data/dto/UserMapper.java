package com.example.spring_data.dto;

import org.springframework.stereotype.Component;

import com.example.spring_data.model.User;

@Component
public class UserMapper {

    public User mapUserRequestToUser(UserRequest userRequest) {
        User user = User.builder()
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .build();

        return user;
    }

}
