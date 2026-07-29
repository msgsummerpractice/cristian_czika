package com.example.spring_data.dto;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.spring_data.model.User;
import com.example.spring_data.service.RoleService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public User mapUserRequestToUser(UserRequest userRequest) {
        User user = User.builder()
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
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

    public User mapUserRequestToUserWithRole(UserRequest userRequest, String roleName) {
        User user = User.builder()
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .role(roleService.getRoleByName(roleName))
                .build();

        return user;
    }

}
