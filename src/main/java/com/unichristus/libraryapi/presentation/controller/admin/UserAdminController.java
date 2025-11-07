package com.unichristus.libraryapi.presentation.controller.admin;

import com.unichristus.libraryapi.application.dto.response.UserResponse;
import com.unichristus.libraryapi.application.usecase.user.UserUseCase;
import com.unichristus.libraryapi.presentation.common.ServiceURI;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "[Admin] Users", description = "Operações administrativas de usuários")
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceURI.ADMIN_USERS)
public class UserAdminController {

    private final UserUseCase userUseCase;

    @Operation(summary = "Buscar usuário por ID (admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{userId}")
    public UserResponse getUserById(@PathVariable UUID userId) {
        return userUseCase.getUserById(userId);
    }

    @Operation(summary = "Listar usuários (admin) paginado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    })
    @GetMapping
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userUseCase.getAllUsers(pageable);
    }

    @Operation(summary = "Remover usuário por ID (admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID userId) {
        userUseCase.deleteUser(userId);
    }
}


