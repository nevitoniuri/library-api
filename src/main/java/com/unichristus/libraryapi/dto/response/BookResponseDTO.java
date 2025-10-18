package com.unichristus.libraryapi.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BookResponseDTO {
    private UUID id;
    private String title;
    private String isbn;
    private Integer numberOfPages;
    private LocalDate publicationDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
