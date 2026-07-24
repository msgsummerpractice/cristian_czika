package com.example.spring_app.repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.example.spring_app.model.User;

@Repository
public class UserRepository {

    private final List<User> users = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    public List<User> findAll() {
        logger.info("Fetching All Users - Returning A Copy of Users List for Security Measures");
        return new ArrayList<>(users);
    }

    public User save(User user) {
        users.stream().filter(u -> u.getId().equals(user.getId())).findFirst().ifPresent(users::remove);

        users.add(user);
        return user;
    }

}
