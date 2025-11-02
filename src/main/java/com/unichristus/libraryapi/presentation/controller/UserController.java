package com.unichristus.libraryapi.presentation.controller;

import com.unichristus.libraryapi.application.common.MapperUtil;
import com.unichristus.libraryapi.application.common.ServiceURIs;
import com.unichristus.libraryapi.application.dto.request.UserUpdateRequest;
import com.unichristus.libraryapi.application.dto.response.UserResponse;
import com.unichristus.libraryapi.domain.user.UserService;
import com.unichristus.libraryapi.infrastructure.security.LoggedUser;
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

    private final UserService userService;

    @GetMapping("/me")
    public UserResponse getMe(@LoggedUser UUID userId) {
        return MapperUtil.parse(userService.findUserByIdOrThrow(userId), UserResponse.class);
    }

    @PutMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMe(
            @LoggedUser UUID userId,
            @RequestBody @Valid UserUpdateRequest dto
    ) {
        userService.updateUser(userId, dto.name(), dto.email(), dto.password());
    }
}