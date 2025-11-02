
package com.unichristus.libraryapi.presentation.controller.admin;

import com.unichristus.libraryapi.application.common.MapperUtil;
import com.unichristus.libraryapi.application.common.ServiceURIs;
import com.unichristus.libraryapi.application.dto.response.UserResponse;
import com.unichristus.libraryapi.domain.common.PageRequestDomain;
import com.unichristus.libraryapi.domain.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceURIs.ADMIN_USERS)
@Tag(name = "Users Admin", description = "Operações administrativas de usuários")
public class UserAdminController{

    private final UserService userService;

    @GetMapping("/{userId}")
    @Operation(summary = "Buscar usuário por ID (admin)")
    public UserResponse getUserById(@PathVariable UUID userId) {
        return MapperUtil.parse(userService.findUserByIdOrThrow(userId), UserResponse.class);
    }

    @GetMapping
    @Operation(summary = "Listar usuários (admin) paginado")
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        PageRequestDomain pageRequest = new PageRequestDomain(pageable.getPageNumber(), pageable.getPageSize());
        List<UserResponse> users = userService.findAll(pageRequest)
                .stream()
                .map(user -> MapperUtil.parse(user, UserResponse.class))
                .toList();
        return new PageImpl<>(users, pageable, users.size());
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remover usuário por ID (admin)")
    public void deleteUser(@PathVariable UUID userId) {
        userService.deleteUserById(userId);
    }
}


