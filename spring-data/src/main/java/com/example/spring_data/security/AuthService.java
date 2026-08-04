package com.example.spring_data.security;

import com.example.spring_data.dto.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.spring_data.exception.EmailAlreadyExistsException;
import com.example.spring_data.exception.InvalidUserRequestException;
import com.example.spring_data.exception.UsernameAlreadyExistsException;
import com.example.spring_data.model.User;
import com.example.spring_data.repository.UserRepository;
import com.example.spring_data.validation.UserValidator;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserValidator userValidator;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final MfaService mfaService;

    public UserResponse signup(UserRequest request) {
        if (!userValidator.validateUserRequest(request)) {
            throw new InvalidUserRequestException();
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException(request.getUsername());
        }

        User user = userMapper.mapUserRequestToUserWithRole(request, "USER");
        User savedUser = userRepository.save(user);
        return userMapper.mapUserToUserResponse(savedUser);
    }

    public SignInResponse signin(SignInRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Wrong username or password."));

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            mfaService.generateCode(request.getUsername());

//            JwtResponse response = JwtResponse.builder().message("MFA Code generated. Please verify to complete login.")
//                    .username(request.getUsername()).build();

            SignInResponse response = SignInResponse.builder().token(jwtService.generateToken(user)).role(user.getRole().getName()).expiresIn(jwtService.getExpirationTime()).build();

            return response;
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Wrong username or password.");
        }
    }

//    public SignInResponse verifyMfa(MfaRequest request) {
//        boolean isValid = mfaService.verifyCode(request.getUsername(), request.getCode());
//        if (!isValid) {
//            throw new BadCredentialsException("Invalid or expired MFA code.");
//        }
//
//        User user = userRepository.findByUsername(request.getUsername())
//                .orElseThrow(() -> new BadCredentialsException("User not found."));
//
//        return SignInResponse.builder()
//                .token(jwtService.generateToken(user))
//                .role(user.getRole().getName())
//                .expiresIn(jwtService.getExpirationTime())
//                .build();
//    }

}
