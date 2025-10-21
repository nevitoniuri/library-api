package com.unichristus.libraryapi.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record FavoriteCreateRequestDTO(
        @NotNull(message = "O ID do usuário é obrigatório")
        UUID userId,

        @NotNull(message = "O ID do livro é obrigatório")
        UUID bookId
) {}