package com.example;

import org.springframework.stereotype.Component;

@Component
public class UserService implements Service {
    
    @Override
    public String serviceInfo() {
        return "(User Service)";
    }

    public String getUserInfo() {
        return "User Info: Full Name: John Doe, Username: john";
    }
}
