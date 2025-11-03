package com.unichristus.libraryapi.application.usecase.auth;

import com.unichristus.libraryapi.application.annotation.UseCase;
import com.unichristus.libraryapi.application.dto.request.LoginRequest;
import com.unichristus.libraryapi.application.dto.request.UserRegisterRequest;
import com.unichristus.libraryapi.application.dto.response.AuthResponse;
import com.unichristus.libraryapi.domain.user.User;
import com.unichristus.libraryapi.domain.user.UserService;
import com.unichristus.libraryapi.domain.user.exception.EmailConflictException;
import com.unichristus.libraryapi.domain.user.exception.UserNotFoundException;
import com.unichristus.libraryapi.infrastructure.security.CustomUserDetails;
import com.unichristus.libraryapi.infrastructure.security.JwtService;
import com.unichristus.libraryapi.infrastructure.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@UseCase
@RequiredArgsConstructor
public class AuthUseCase {

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
            throw new EmailConflictException(request.email());
        }
        User savedUser = userService.save(
                User.builder()
                        .name(request.name().trim())
                        .email(request.email())
                        .password(passwordEncoder.encode(request.password()))
                        .role(Role.USER)
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
                .orElseThrow(() -> new UserNotFoundException(request.email()));
        return generateToken(user);
    }
}