package com.unichristus.libraryapi.application.dto.response;

import java.util.List;

public record HomeResponse(
        List<ReadingHomeResponse> readings,
        UserSummaryResponse userSummary
) {
}
