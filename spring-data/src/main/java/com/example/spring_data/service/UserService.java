package com.example.spring_data.service;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.spring_data.dto.UserMapper;
import com.example.spring_data.dto.UserRequest;
import com.example.spring_data.dto.UserResponse;
import com.example.spring_data.exception.EmailAlreadyExistsException;
import com.example.spring_data.exception.InvalidUserRequestException;
import com.example.spring_data.exception.UserNotFoundException;
import com.example.spring_data.exception.UsernameAlreadyExistsException;
import com.example.spring_data.model.User;
import com.example.spring_data.repository.UserRepository;
import com.example.spring_data.validation.UserValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserValidator userValidator;

    public List<UserResponse> getAllUsers(Pageable pageable) {
        List<User> users = userRepository.findAll(pageable).getContent();
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
        if (!userValidator.validateUserRequest(request)) {
            throw new InvalidUserRequestException();
        }

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
        if (!userValidator.validateUserRequest(request)) {
            throw new InvalidUserRequestException();
        }

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

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        User updatedUser = userRepository.save(user);
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

        if (userValidator.isUsernameValid(request.getUsername())) {
            user.setUsername(request.getUsername());
        }

        if (userValidator.isEmailValid(request.getEmail())) {
            user.setEmail(request.getEmail());
        }

        if (userValidator.isPasswordValid(request.getPassword())) {
            user.setPassword(request.getPassword());
        }

        if (userValidator.isFirstNameValid(request.getFirstName())) {
            user.setFirstName(request.getFirstName());
        }

        if (userValidator.isLastNameValid(request.getLastName())) {
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
