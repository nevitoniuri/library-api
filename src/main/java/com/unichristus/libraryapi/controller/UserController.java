package com.unichristus.libraryapi.controller;

import com.unichristus.libraryapi.dto.request.UserUpdateRequest;
import com.unichristus.libraryapi.dto.response.UserResponse;
import com.unichristus.libraryapi.service.UserService;
import com.unichristus.libraryapi.util.MapperUtil;
import com.unichristus.libraryapi.util.ServiceURIs;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceURIs.USERS_RESOURCE)
public class UserController {

    private final UserService userService;

    //TODO: Rota privilegiada, Levar para UserAdminController
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable UUID id) {
        return MapperUtil.parse(userService.findUserByIdOrThrow(id), UserResponse.class);
    }

    //TODO: Rota privilegiada, Levar para UserAdminController
    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.findAll().stream()
                .map(user -> MapperUtil.parse(user, UserResponse.class))
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //TODO: receber entidade completa e atualizar todos os campos? ou manter como está?
    public void updateUser(@PathVariable UUID id, @RequestBody @Valid UserUpdateRequest dto) {
        userService.updateUser(id, dto);
    }

    //TODO: Rota privilegiada, Levar para UserAdminController
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }
}