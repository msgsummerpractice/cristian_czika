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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.spring_data.dto.UserMapper;
import com.example.spring_data.dto.UserRequest;
import com.example.spring_data.dto.UserResponse;
import com.example.spring_data.exception.EmailAlreadyExistsException;
import com.example.spring_data.exception.UserNotFoundException;
import com.example.spring_data.exception.UsernameAlreadyExistsException;
import com.example.spring_data.model.User;
import com.example.spring_data.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

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

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());

        Page<User> userPage = new PageImpl<>(List.of(user));

        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);

        when(userMapper.mapUserToUserResponse(user)).thenReturn(userResponse);

        List<UserResponse> result = userService.getAllUsers();

        assertEquals(1, result.size());
    }

    @Test
    void getUserById_ShouldReturnUser() {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.mapUserToUserResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.getUserById(1L);

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
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail("cristi@gmail.com");

        when(userRepository.findByUsername("cristi")).thenReturn(Optional.of(user));
        when(userMapper.mapUserToUserResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.getUserByUsername("cristi");

        assertEquals("cristi@gmail.com", result.getEmail());
    }

    @Test
    void getUserByUsername_WhenDoesNotExist_ShouldThrowException() {
        when(userRepository.findByUsername("invalid")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserByUsername("invalid"));
    }

    @Test
    void addUser_Success_ShouldReturnSavedUser() {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setUsername(userRequest.getUsername());

        when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(userRequest.getUsername())).thenReturn(false);

        when(userMapper.mapUserRequestToUser(any(UserRequest.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.mapUserToUserResponse(user)).thenReturn(userResponse);

        UserResponse savedUser = userService.addUser(userRequest);

        assertNotNull(savedUser);
        assertEquals(1L, savedUser.getId());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void addUser_WhenEmailExists_ShouldThrowException() {
        when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> userService.addUser(userRequest));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void addUser_WhenUsernameExists_ShouldThrowException() {
        when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(userRequest.getUsername())).thenReturn(true);

        assertThrows(UsernameAlreadyExistsException.class, () -> userService.addUser(userRequest));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateUser_Success_NoFieldChanges_ShouldUpdate() {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setUsername("cristi");
        userResponse.setEmail("cristi@gmail.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.mapUserToUserResponse(any(User.class))).thenReturn(userResponse);

        UserResponse updated = userService.updateUser(1L, userRequest);

        assertNotNull(updated);
        assertEquals("cristi", updated.getUsername());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUser_Success_WithNewEmailAndUsername_ShouldUpdate() {
        userRequest.setEmail("new@gmail.com");
        userRequest.setUsername("new");

        UserResponse userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setEmail("new@gmail.com");
        userResponse.setUsername("new");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail("new@gmail.com")).thenReturn(false);
        when(userRepository.existsByUsername("new")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.mapUserToUserResponse(any(User.class))).thenReturn(userResponse);

        UserResponse updated = userService.updateUser(1L, userRequest);

        assertNotNull(updated);
        assertEquals("new@gmail.com", updated.getEmail());
        assertEquals("new", updated.getUsername());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUser_WhenEmailTakenByAnotherUser_ShouldThrowException() {
        userRequest.setEmail("otheremail@gmail.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail("otheremail@gmail.com")).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> userService.updateUser(1L, userRequest));
        verify(userRepository, never()).save(any(User.class));
    }

}
