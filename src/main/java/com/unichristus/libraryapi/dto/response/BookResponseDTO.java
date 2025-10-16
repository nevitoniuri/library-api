package com.unichristus.libraryapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDTO {
    private UUID id;
    private String title;
    private String isbn;
    private String numberOfPages;
    private String publishedYear;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
