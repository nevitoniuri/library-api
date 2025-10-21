package com.unichristus.libraryapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteResponseDTO {
    private UUID id;
    private UUID userId;
    private UUID bookId;
    private String bookTitle;
    private String bookIsbn;
    private LocalDateTime createdAt;
}
