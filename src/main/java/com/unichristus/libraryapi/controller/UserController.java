package com.unichristus.libraryapi.controller;

import com.unichristus.libraryapi.dto.request.UserCreateRequestDTO;
import com.unichristus.libraryapi.dto.request.UserUpdateRequestDTO;
import com.unichristus.libraryapi.dto.response.UserResponseDTO;
import com.unichristus.libraryapi.service.UserService;
import com.unichristus.libraryapi.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO createUser(@RequestBody @Valid UserCreateRequestDTO dto) {
        return MapperUtil.parse(userService.createUser(dto), UserResponseDTO.class);
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable UUID id) {
        return MapperUtil.parse(userService.findUserByIdOrThrow(id), UserResponseDTO.class);
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.findAll().stream()
                .map(user -> MapperUtil.parse(user, UserResponseDTO.class))
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable UUID id, @RequestBody @Valid UserUpdateRequestDTO dto) {
        userService.updateUser(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }
}