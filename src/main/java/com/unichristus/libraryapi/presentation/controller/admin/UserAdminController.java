
package com.unichristus.libraryapi.presentation.controller.admin;

import com.unichristus.libraryapi.application.dto.response.UserResponse;
import com.unichristus.libraryapi.application.util.MapperUtil;
import com.unichristus.libraryapi.application.util.ServiceURIs;
import com.unichristus.libraryapi.domain.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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


