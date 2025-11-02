package com.unichristus.libraryapi.presentation.controller;

import com.unichristus.libraryapi.application.dto.request.UserUpdateRequest;
import com.unichristus.libraryapi.application.dto.response.UserResponse;
import com.unichristus.libraryapi.application.usecase.user.UserUseCase;
import com.unichristus.libraryapi.infrastructure.security.LoggedUser;
import com.unichristus.libraryapi.presentation.common.ServiceURIs;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceURIs.USERS_RESOURCE)
@Tag(name = "Users", description = "Operações com usuário autenticado")
public class UserController {

    private final UserUseCase userUseCase;

    @GetMapping("/me")
    public UserResponse getMe(@LoggedUser UUID userId) {
        return userUseCase.getUserById(userId);
    }

    @PutMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMe(
            @LoggedUser UUID userId,
            @RequestBody @Valid UserUpdateRequest requestDto
    ) {
        userUseCase.updateUser(userId, requestDto);
    }
}