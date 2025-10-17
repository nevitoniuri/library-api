package com.unichristus.libraryapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
    private Integer numberOfPages;
    private LocalDate publicationDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
