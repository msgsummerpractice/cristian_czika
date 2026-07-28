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

    public UserResponse mapUserToUserResponse(User user) {
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();

        return userResponse;
    }

}
