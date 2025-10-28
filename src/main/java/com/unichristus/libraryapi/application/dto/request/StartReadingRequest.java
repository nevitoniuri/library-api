package com.unichristus.libraryapi.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "Dados para iniciar uma leitura")
public record StartReadingRequest(
        @NotNull
        @Schema(description = "ID do livro a ser lido", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID bookId
) {
}