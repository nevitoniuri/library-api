package com.unichristus.libraryapi.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

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
        LocalDate publicationDate
) {
}
