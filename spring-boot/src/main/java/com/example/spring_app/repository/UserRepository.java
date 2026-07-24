package com.example.spring_app.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.spring_app.model.User;

@Repository
public class UserRepository {

    private final List<User> users = new ArrayList<>();

    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    public User save(User user) {
        users.stream().filter(u -> u.getId().equals(user.getId())).findFirst().ifPresent(users::remove);

        users.add(user);
        return user;
    }

}
