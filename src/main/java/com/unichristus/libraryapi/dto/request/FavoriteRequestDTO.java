package com.unichristus.libraryapi.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteRequestDTO {
    @NotNull(message = "O ID do usuário é obrigatório")
    private UUID userId;

    @NotNull(message = "O ID do livro é obrigatório")
    private UUID bookId;
}
