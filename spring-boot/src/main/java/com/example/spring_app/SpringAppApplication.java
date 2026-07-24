package com.example.spring_app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.spring_app.config.MailProperties;

@SpringBootApplication
@EnableConfigurationProperties(MailProperties.class)
public class SpringAppApplication {

    private static final Logger logger = LoggerFactory.getLogger(SpringAppApplication.class);

    public static void main(String[] args) {
        logger.info("Starting User Management Application");
        SpringApplication.run(SpringAppApplication.class, args);
    }

}
