package com.unichristus.libraryapi.application.usecase.user;

import com.unichristus.libraryapi.application.annotation.UseCase;
import com.unichristus.libraryapi.application.dto.request.UserUpdateRequest;
import com.unichristus.libraryapi.application.dto.response.UserResponse;
import com.unichristus.libraryapi.application.mapper.UserResponseMapper;
import com.unichristus.libraryapi.domain.user.User;
import com.unichristus.libraryapi.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@UseCase
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
        return userService.findAll(pageable).map(UserResponseMapper::toUserResponse);
    }

    public void invalidateUser(UUID userId) {
        User user = userService.findUserByIdOrThrow(userId);
        userService.invalidateUser(user);
    }
}
