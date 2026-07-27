package com.example.spring_data.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

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

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(u -> convertToResponse(u)).toList();
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " was not found!"));
        return convertToResponse(user);
    }

    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username " + username + " was not found!"));
        return convertToResponse(user);
    }

    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " was not found!"));
        return convertToResponse(user);
    }

    public UserResponse addUser(UserRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email " + request.getEmail() + " is already in use.");
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("Username " + request.getUsername() + " is already in use.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        User savedUser = userRepository.save(user);
        return convertToResponse(savedUser);
    }

    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " was not found!"));

        if (userRepository.findByEmail(request.getEmail()).isPresent()
                && !Objects.equals(request.getEmail(), user.getEmail())) {
            throw new EmailAlreadyExistsException("Email " + request.getEmail() + " is already in use.");
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()
                && !Objects.equals(request.getUsername(), user.getUsername())) {
            throw new UsernameAlreadyExistsException("Username " + request.getUsername() + " is already in use.");
        }

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        User updatedUser = userRepository.save(user);
        return convertToResponse(updatedUser);
    }

    public UserResponse patchUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " was not found!"));

        if (userRepository.findByEmail(request.getEmail()).isPresent()
                && !Objects.equals(request.getEmail(), user.getEmail())) {
            throw new EmailAlreadyExistsException("Email " + request.getEmail() + " is already in use.");
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()
                && !Objects.equals(request.getUsername(), user.getUsername())) {
            throw new UsernameAlreadyExistsException("Username " + request.getUsername() + " is already in use.");
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

        User patchedUser = userRepository.save(user);
        return convertToResponse(patchedUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public int getUserCount() {
        return userRepository.countUsers();
    }

    public List<UserResponse> searchTop10UsersByUsernameIgnoreCaseOrderByUsernameAsc(String username) {
        List<User> users = userRepository.findTop10ByUsernameIgnoreCaseOrderByUsernameAsc(username);
        return users.stream().map(u -> convertToResponse(u)).toList();
    }

    private UserResponse convertToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        return response;
    }

}
