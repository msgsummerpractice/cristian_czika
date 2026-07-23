package com.example;

import org.springframework.stereotype.Component;

@Component
public class EmailService implements Service {
    @Override
    public String serviceInfo() {
        return "(Email Service)";
    }
}
