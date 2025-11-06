package com.unichristus.libraryapi.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record BookCreateRequest(
        @NotBlank
        String title,

        @NotBlank
        String isbn,

        @NotNull
        @Positive
        Integer numberOfPages,

        @NotNull
        @Past
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate publicationDate,

        @NotBlank
        String coverUrl,

        List<UUID> categories
) {
}
