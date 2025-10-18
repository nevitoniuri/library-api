package com.unichristus.libraryapi.dto.response;

import java.util.List;

public record HomeResponseDTO(
        List<ReadingResponseDTO> readings,
        Integer totalBooksReading,
        Integer totalBooksRead,
        Integer totalPagesRead
) {
}
