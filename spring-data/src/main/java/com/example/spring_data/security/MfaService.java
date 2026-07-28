package com.example.spring_data.security;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MfaService {

    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();

    public String generateOtp(String username) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        otpStorage.put(username, otp);

        System.out.println("MFA for user " + username + ": " + otp);

        return otp;
    }

    public boolean verifyOtp(String username, String code) {
        String storedOtp = otpStorage.get(username);

        if (storedOtp != null && Objects.equals(storedOtp, code)) {
            otpStorage.remove(username);
            return true;
        }

        return false;
    }

}
