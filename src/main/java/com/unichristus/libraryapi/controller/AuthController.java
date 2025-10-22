package com.unichristus.libraryapi.controller;

import com.unichristus.libraryapi.dto.request.LoginRequest;
import com.unichristus.libraryapi.dto.request.UserRegisterRequest;
import com.unichristus.libraryapi.dto.response.AuthResponse;
import com.unichristus.libraryapi.security.CustomUserDetails;
import com.unichristus.libraryapi.service.AuthService;
import com.unichristus.libraryapi.util.ServiceURIs;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints para autenticação de usuários")
@RequestMapping(ServiceURIs.AUTH)
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid UserRegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(Map.of(
                "id", userDetails.getId(),
                "name", userDetails.getName(),
                "email", userDetails.getEmail()
        ));
    }
}
