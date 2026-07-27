package com.example.spring_data.model;

import org.springframework.stereotype.Component;

import com.example.spring_data.dto.UserRequest;

@Component
public class UserMapper {

    public static User mapUserRequestToUser(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        return user;
    }

}
