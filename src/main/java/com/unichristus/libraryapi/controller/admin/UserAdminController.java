
package com.unichristus.libraryapi.controller.admin;

import com.unichristus.libraryapi.dto.response.UserResponse;
import com.unichristus.libraryapi.service.UserService;
import com.unichristus.libraryapi.util.MapperUtil;
import com.unichristus.libraryapi.util.ServiceURIs;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceURIs.ADMIN_USERS)
@Tag(name = "Users Admin", description = "Operações administrativas de usuários")
public class UserAdminController{

    private final UserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID (admin)")
    public UserResponse getUserById(@PathVariable UUID id) {
        return MapperUtil.parse(userService.findUserByIdOrThrow(id), UserResponse.class);
    }

    @GetMapping
    @Operation(summary = "Listar usuários (admin)")
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userService.findAll(pageable)
                .map(user -> MapperUtil.parse(user, UserResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remover usuário por ID (admin)")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }
}


