package com.unichristus.libraryapi.dto.response;

public record UserSummaryResponse(
        Integer totalInProgress,
        Integer totalFinished,
        Integer totalPagesRead
) {
}
