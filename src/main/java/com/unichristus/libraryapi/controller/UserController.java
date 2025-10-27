package com.unichristus.libraryapi.controller;

import com.unichristus.libraryapi.annotation.LoggedUser;
import com.unichristus.libraryapi.dto.request.UserUpdateRequest;
import com.unichristus.libraryapi.dto.response.UserResponse;
import com.unichristus.libraryapi.security.CustomUserDetails;
import com.unichristus.libraryapi.service.UserService;
import com.unichristus.libraryapi.util.MapperUtil;
import com.unichristus.libraryapi.util.ServiceURIs;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceURIs.USERS_RESOURCE)
@Tag(name = "Users", description = "Operações com usuário autenticado")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserResponse getMe(@LoggedUser CustomUserDetails userDetails) {
        return MapperUtil.parse(userService.findUserByIdOrThrow(userDetails.getId()), UserResponse.class);
    }

    @PutMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMe(@RequestBody @Valid UserUpdateRequest dto, @LoggedUser CustomUserDetails userDetails) {
        userService.updateUser(userDetails.getId(), dto);
    }
}