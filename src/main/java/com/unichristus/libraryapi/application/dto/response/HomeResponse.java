package com.unichristus.libraryapi.application.dto.response;

import java.util.List;

public record HomeResponse(
        List<ReadingResponse> readings,
        UserSummaryResponse  userSummary
) {
}
