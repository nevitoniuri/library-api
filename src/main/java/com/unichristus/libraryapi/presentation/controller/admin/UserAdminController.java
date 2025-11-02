package com.unichristus.libraryapi.presentation.controller.admin;

import com.unichristus.libraryapi.application.dto.response.UserResponse;
import com.unichristus.libraryapi.application.usecase.user.UserUseCase;
import com.unichristus.libraryapi.presentation.common.ServiceURIs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceURIs.ADMIN_USERS)
@Tag(name = "Users Admin", description = "Operações administrativas de usuários")
public class UserAdminController {

    private final UserUseCase userUseCase;

    @GetMapping("/{userId}")
    @Operation(summary = "Buscar usuário por ID (admin)")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID userId) {
        return ResponseEntity.ok(userUseCase.getUserById(userId));
    }

    @GetMapping
    @Operation(summary = "Listar usuários (admin) paginado")
    public ResponseEntity<Page<UserResponse>> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(userUseCase.getAllUsers(pageable));
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remover usuário por ID (admin)")
    public void deleteUser(@PathVariable UUID userId) {
        userUseCase.deleteUser(userId);
    }
}


