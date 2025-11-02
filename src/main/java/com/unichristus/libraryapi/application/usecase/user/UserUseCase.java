package com.unichristus.libraryapi.application.usecase.user;

import com.unichristus.libraryapi.application.dto.request.UserUpdateRequest;
import com.unichristus.libraryapi.application.dto.response.UserResponse;
import com.unichristus.libraryapi.application.mapper.UserResponseMapper;
import com.unichristus.libraryapi.domain.common.PageRequestDomain;
import com.unichristus.libraryapi.domain.user.User;
import com.unichristus.libraryapi.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserUseCase {

    private final UserService userService;

    public UserResponse getUserById(UUID userId) {
        User user = userService.findUserByIdOrThrow(userId);
        return UserResponseMapper.toUserResponse(user);
    }

    public void updateUser(UUID userId, UserUpdateRequest dto) {
        userService.updateUser(userId, dto.name(), dto.email(), dto.password());
    }

    public Page<UserResponse> getAllUsers(Pageable pageable) {
        var pageRequest = new PageRequestDomain(
                pageable.getPageNumber(),
                pageable.getPageSize()
        );
        return userService.findAll(pageRequest)
                .map(UserResponseMapper::toUserResponse);
    }

    public void deleteUser(UUID userId) {
        userService.deleteUserById(userId);
    }
}
