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

    public AuthResponse register(UserRegisterRequest request) {
        // Verifica se email já existe
        if (userService.existsByEmail(request.email())) {
            throw new RuntimeException("Email já cadastrado");
        }

        // Cria novo usuário
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setActive(true);

        User savedUser = userService.save(user);

        // Gera token
        CustomUserDetails userDetails = CustomUserDetails.from(savedUser);
        String token = jwtService.generateToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .userId(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        // Autentica
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Busca usuário
        User user = userService.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new ServiceException(ServiceError.USER_NOT_FOUND));

        // Gera token
        CustomUserDetails userDetails = CustomUserDetails.from(user);
        String token = jwtService.generateToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}