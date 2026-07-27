package com.example.spring_app.repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.spring_app.model.User;

public class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = new UserRepository();
    }

    @Test
    public void shouldSaveAndFindAllUsers() {
        User cristi = new User(0l, "Cristi", "Czika", "cristi", "cristiczika@gmail.com", "cristi123", "ADMIN");
        User andrei = new User(1l, "Andrei", "Preda", "andrei", "andrei@gmail.com", "andrei123", "USER");
        User maria = new User(2l, "Maria", "Ioana", "maria", "mariaioana@gmail.com", "maria123", "USER");

        userRepository.save(cristi);
        userRepository.save(andrei);
        userRepository.save(maria);

        List<User> result = userRepository.findAll();

        assertEquals(3, result.size());
        assertEquals("cristi", result.get(0).getUsername());
        assertEquals("andrei", result.get(1).getUsername());
        assertEquals("maria", result.get(2).getUsername());
        assertEquals("cristiczika@gmail.com", result.get(0).getEmail());
        assertEquals("andrei@gmail.com", result.get(1).getEmail());
        assertEquals("mariaioana@gmail.com", result.get(2).getEmail());
    }

    @Test
    public void shouldUpdateUserIfIdAlreadyExists() {
        User cristi = new User(0l, "Cristi", "Czika", "cristi", "cristiczika@gmail.com", "cristi123", "ADMIN");

        userRepository.save(cristi);

        User updatedCristi = new User(0l, "Cristi", "Czika", "cristi", "cristiczika@gmail.com", "cristi1234", "ADMIN");
        userRepository.save(updatedCristi);

        List<User> result = userRepository.findAll();

        assertEquals(1, result.size());
        assertEquals("cristi1234", result.get(0).getPassword());
    }

    @Test
    public void shouldReturnEmptyListWhenNoUsersExist() {
        List<User> result = userRepository.findAll();

        assertEquals(0, result.size());
    }

}
