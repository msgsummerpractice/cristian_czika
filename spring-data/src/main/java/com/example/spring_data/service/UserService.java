package com.example.spring_data.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.example.spring_data.dto.UserMapper;
import com.example.spring_data.dto.UserRequest;
import com.example.spring_data.dto.UserResponse;
import com.example.spring_data.exception.EmailAlreadyExistsException;
import com.example.spring_data.exception.UserNotFoundException;
import com.example.spring_data.exception.UsernameAlreadyExistsException;
import com.example.spring_data.model.User;
import com.example.spring_data.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::mapUserToUserResponse).toList();
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException());

        return userMapper.mapUserToUserResponse(user);
    }

    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException());

        return userMapper.mapUserToUserResponse(user);
    }

    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException());

        return userMapper.mapUserToUserResponse(user);
    }

    public UserResponse addUser(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException(request.getUsername());
        }

        User user = userMapper.mapUserRequestToUser(request);
        User savedUser = userRepository.save(user);
        return userMapper.mapUserToUserResponse(savedUser);
    }

    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException());

        if (userRepository.existsByEmail(request.getEmail())
                && !Objects.equals(user.getEmail(), request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        if (userRepository.existsByUsername(request.getUsername())
                && !Objects.equals(user.getUsername(), request.getUsername())) {
            throw new UsernameAlreadyExistsException(request.getUsername());
        }

        User updatedUser = userRepository.save(userMapper.mapUserRequestToUser(request));
        return userMapper.mapUserToUserResponse(updatedUser);
    }

    public UserResponse patchUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException());

        if (userRepository.existsByEmail(request.getEmail())
                && !Objects.equals(user.getEmail(), request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        if (userRepository.existsByUsername(request.getUsername())
                && !Objects.equals(user.getUsername(), request.getUsername())) {
            throw new UsernameAlreadyExistsException(request.getUsername());
        }

        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }

        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        if (request.getPassword() != null) {
            user.setPassword(request.getPassword());
        }

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }

        return userMapper.mapUserToUserResponse(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }

        userRepository.deleteById(id);
    }

    public int getUserCount() {
        return userRepository.countUsers();
    }

    public List<UserResponse> searchTop10UsersByUsernameIgnoreCaseOrderByUsernameAsc(String username) {
        List<User> users = userRepository.findTop10ByUsernameIgnoreCaseOrderByUsernameAsc(username);
        return users.stream().map(userMapper::mapUserToUserResponse).toList();
    }

}
