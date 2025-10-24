package com.unichristus.libraryapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Dados para atualizar o progresso de leitura de um livro")
public record UpdateReadingProgressRequest(
        @NotNull
        @Positive
        @Schema(description = "Página atual da leitura", example = "150")
        Integer currentPage
) {
}
