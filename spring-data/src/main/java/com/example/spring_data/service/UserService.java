package com.example.spring_data.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.example.spring_data.dto.UserMapper;
import com.example.spring_data.dto.UserRequest;
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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException());
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException());
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException());
    }

    public User addUser(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException(request.getUsername());
        }

        User user = userMapper.mapUserRequestToUser(request);
        return userRepository.save(user);
    }

    public User updateUser(Long id, UserRequest request) {
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

        return userRepository.save(userMapper.mapUserRequestToUser(request));
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

    public List<User> searchTop10UsersByUsernameIgnoreCaseOrderByUsernameAsc(String username) {
        return userRepository.findTop10ByUsernameIgnoreCaseOrderByUsernameAsc(username);
    }

}
