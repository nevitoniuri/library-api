package com.unichristus.libraryapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record BookCreateRequestDTO(
        @NotBlank
        String title,

        @NotBlank
        String isbn,

        @NotNull
        @Positive
        Integer numberOfPages,

        @NotBlank
        @Pattern(regexp = "^\\d{4}$", message = "Published year must be a valid 4-digit year")
        String publishedYear
) {
}
