package com.unichristus.libraryapi.controller;

import com.unichristus.libraryapi.dto.request.UserRequestDTO;
import com.unichristus.libraryapi.dto.response.UserResponseDTO;
import com.unichristus.libraryapi.model.User;
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
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponseDTO createUser(@RequestBody @Valid UserRequestDTO userDTO) {
        User createdUser = userService.createUser(userDTO);
        return MapperUtil.parse(createdUser, UserResponseDTO.class);
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable UUID id) {
        User user = userService.findUserByIdOrThrow(id);
        return MapperUtil.parse(user, UserResponseDTO.class);
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.findAll().stream()
                .map(user -> MapperUtil.parse(user, UserResponseDTO.class))
                .toList();
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable UUID id, @RequestBody @Valid UserRequestDTO userDTO) {
        userService.updateUser(id, userDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }
}