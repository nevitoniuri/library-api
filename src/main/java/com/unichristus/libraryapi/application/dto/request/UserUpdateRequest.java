package com.unichristus.libraryapi.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
        String name,

        @Email
        String email,

        @Size(min = 6, max = 64)
        String password
) {
}