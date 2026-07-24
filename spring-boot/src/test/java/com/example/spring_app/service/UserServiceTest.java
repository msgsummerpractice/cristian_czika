package com.example.spring_app.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.spring_app.model.User;
import com.example.spring_app.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void shouldReturnAllUsersFromRepository() {
        List<User> mockUsers = List.of(
                new User(0l, "Cristi", "Czika", "cristi", "cristiczika@gmail.com", "cristi123", "ADMIN"),
                new User(1l, "Andrei", "Preda", "andrei", "andrei@gmail.com", "andrei123", "USER"),
                new User(2l, "Maria", "Ioana", "maria", "mariaioana@gmail.com", "maria123", "USER")
        );

        Mockito.when(userRepository.findAll()).thenReturn(mockUsers);

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("cristi", result.get(0).getUsername());
        assertEquals("andrei", result.get(1).getUsername());
        assertEquals("maria", result.get(2).getUsername());
        assertEquals("cristiczika@gmail.com", result.get(0).getEmail());
    }

}
