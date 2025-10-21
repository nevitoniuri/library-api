package com.unichristus.libraryapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para iniciar uma leitura")
public class StartReadingRequest {

    @NotNull(message = "ID do livro é obrigatório")
    @Schema(description = "ID do livro a ser lido", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID bookId;
}