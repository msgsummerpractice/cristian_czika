package com.example.spring_data.security;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MfaService {

    private final Map<String, String> codeStorage = new ConcurrentHashMap<>();

    public String generateCode(String username) {
        String code = String.format("%06d", new Random().nextInt(999999));
        codeStorage.put(username, code);

        System.out.println("MFA for user " + username + ": " + code);

        return code;
    }

    public boolean verifyCode(String username, String code) {
        String storedCode = codeStorage.get(username);

        if (storedCode != null && Objects.equals(storedCode, code)) {
            codeStorage.remove(username);
            return true;
        }

        return false;
    }

}
