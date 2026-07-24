package com.example.spring_data.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.spring_data.dto.UserRequest;
import com.example.spring_data.exception.EmailAlreadyExistsException;
import com.example.spring_data.exception.UserNotFoundException;
import com.example.spring_data.exception.UsernameAlreadyExistsException;
import com.example.spring_data.model.User;
import com.example.spring_data.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserRequest userRequest;

    @BeforeEach
    void setup() {
        user = new User();
        user.setId(1L);
        user.setUsername("cristi");
        user.setEmail("cristi@gmail.com");
        user.setPassword("cristi123");
        user.setFirstName("Cristi");
        user.setLastName("Czika");

        userRequest = new UserRequest();
        userRequest.setUsername("cristi");
        userRequest.setEmail("cristi@gmail.com");
        userRequest.setPassword("cristi123");
        userRequest.setFirstName("Cristi");
        userRequest.setLastName("Czika");
    }

    // Get All
    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> result = userService.getAllUsers();

        assertEquals(1, result.size());
    }

    // Get By
    @Test
    void getUserById_ShouldReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    void getUserById_WhenUserDoesNotExist_ShouldThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void getUserByUsername_WhenExists_ShouldReturnUser() {
        when(userRepository.findByUsername("cristi")).thenReturn(Optional.of(user));

        User result = userService.getUserByUsername("cristi");

        assertEquals("cristi@gmail.com", result.getEmail());
    }

    @Test
    void getUserByUsername_WhenDoesNotExist_ShouldThrowException() {
        when(userRepository.findByUsername("invalid")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserByUsername("invalid"));
    }

    // Add User
    @Test
    void addUser_Success_ShouldReturnSavedUser() {
        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(userRequest.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.addUser(userRequest);

        assertNotNull(savedUser);
        assertEquals(1L, savedUser.getId());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void addUser_WhenEmailExists_ShouldThrowException() {
        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(Optional.of(user));

        assertThrows(EmailAlreadyExistsException.class, () -> userService.addUser(userRequest));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void addUser_WhenUsernameExists_ShouldThrowException() {
        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(userRequest.getUsername())).thenReturn(Optional.of(user));

        assertThrows(UsernameAlreadyExistsException.class, () -> userService.addUser(userRequest));
        verify(userRepository, never()).save(any(User.class));
    }

    // Update User
    @Test
    void updateUser_Success_NoFieldChanges_ShouldUpdate() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updated = userService.updateUser(1L, userRequest);

        assertNotNull(updated);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUser_Success_WithNewEmailAndUsername_ShouldUpdate() {
        userRequest.setEmail("new@gmail.com");
        userRequest.setUsername("new");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail("new@gmail.com")).thenReturn(Optional.empty());
        when(userRepository.findByUsername("new")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.updateUser(1L, userRequest);
    }

    @Test
    void updateUser_WhenEmailTakenByAnotherUser_ShouldThrowException() {
        userRequest.setEmail("otheremail@gmail.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail("otheremail@gmail.com")).thenReturn(Optional.of(new User()));

        assertThrows(EmailAlreadyExistsException.class, () -> userService.updateUser(1L, userRequest));
    }

}
