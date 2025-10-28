package com.unichristus.libraryapi.presentation.controller;

import com.unichristus.libraryapi.application.dto.request.LoginRequest;
import com.unichristus.libraryapi.application.dto.request.UserRegisterRequest;
import com.unichristus.libraryapi.application.dto.response.AuthResponse;
import com.unichristus.libraryapi.application.usecase.auth.AuthUseCase;
import com.unichristus.libraryapi.application.util.ServiceURIs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Autenticação de usuários")
@RequestMapping(ServiceURIs.AUTH_RESOURCE)
public class AuthController {

    private final AuthUseCase authUseCase;

    @Operation(summary = "Registrar novo usuário", description = "Registra um novo usuário no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Usuário já existe")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid UserRegisterRequest request) {
        return ResponseEntity.ok(authUseCase.register(request));
    }

    @Operation(summary = "Login de usuário", description = "Realiza o login de um usuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authUseCase.login(request));
    }
}
