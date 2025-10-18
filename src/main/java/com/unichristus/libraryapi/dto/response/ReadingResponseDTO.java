package com.unichristus.libraryapi.dto.response;

import com.unichristus.libraryapi.enums.ReadingStatus;
import com.unichristus.libraryapi.model.Reading;
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
public class ReadingResponseDTO {
    private UUID id;
    private BookLowResponseDTO book;
    private ReadingStatus status;
    private Integer currentPage;
    private Double percentRead;
    private LocalDateTime startedAt;
    private LocalDateTime lastReadedAt;
    private LocalDateTime finishedAt;
}
