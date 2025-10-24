package com.unichristus.libraryapi.service;

import com.unichristus.libraryapi.dto.request.LoginRequest;
import com.unichristus.libraryapi.dto.request.UserRegisterRequest;
import com.unichristus.libraryapi.dto.response.AuthResponse;
import com.unichristus.libraryapi.exception.ServiceError;
import com.unichristus.libraryapi.exception.ServiceException;
import com.unichristus.libraryapi.model.User;
import com.unichristus.libraryapi.security.CustomUserDetails;
import com.unichristus.libraryapi.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private AuthResponse generateToken(User user) {
        CustomUserDetails userDetails = CustomUserDetails.from(user);
        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token, "Bearer", user.getId(), user.getName(), user.getEmail());
    }

    public AuthResponse register(UserRegisterRequest request) {
        if (userService.existsByEmail(request.email())) {
            throw new ServiceException(ServiceError.EMAIL_ALREADY_EXISTS, request.email());
        }
        User savedUser = userService.save(
                User.builder()
                        .name(request.name().trim())
                        .email(request.email())
                        .password(passwordEncoder.encode(request.password()))
                        .active(Boolean.TRUE).build()
        );
        return generateToken(savedUser);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password().trim()
                )
        );
        User user = userService.findUserByEmail(request.email())
                .orElseThrow(() -> new ServiceException(ServiceError.USER_NOT_FOUND));
        return generateToken(user);
    }
}