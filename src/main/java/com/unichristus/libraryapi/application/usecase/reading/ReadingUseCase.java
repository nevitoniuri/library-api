package com.unichristus.libraryapi.application.usecase.reading;

import com.unichristus.libraryapi.application.annotation.UseCase;
import com.unichristus.libraryapi.application.dto.response.ReadingResponse;
import com.unichristus.libraryapi.application.mapper.ReadingResponseMapper;
import com.unichristus.libraryapi.domain.reading.Reading;
import com.unichristus.libraryapi.domain.reading.ReadingService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class ReadingUseCase {

    private final ReadingService readingService;

    public ReadingResponse syncReading(UUID userId, UUID bookId, Integer currentPage) {
        Reading reading = readingService.findReadingInProgressOrCreateReading(userId, bookId);
        readingService.updateReadingProgress(reading, currentPage);
        return ReadingResponseMapper.toReadingResponse(reading);
    }
}
