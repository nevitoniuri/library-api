package com.unichristus.libraryapi.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Pontuação média de um livro")
public record BookAverageScoreResponse(
        @Schema(description = "ID do livro", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID bookId,

        @Schema(description = "Pontuação média (1.0 a 5.0)", example = "4.5")
        Double averageRating,

        @Schema(description = "Total de avaliações", example = "42")
        Long totalReviews
) {
}