package com.example.spring_app.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.example.spring_app.model.User;
import com.example.spring_app.repository.UserRepository;

@Configuration
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.save(new User(0l, "Cristi", "Czika", "cristi", "cristiczika@gmail.com", "cristi123", "ADMIN"));
        userRepository.save(new User(1l, "Andrei", "Preda", "andrei", "andrei@gmail.com", "andrei123", "USER"));
        userRepository.save(new User(2l, "Maria", "Ioana", "maria", "mariaioana@gmail.com", "maria123", "USER"));
    }

}
