package com.unichristus.libraryapi.mapper;

import com.unichristus.libraryapi.dto.response.BookLowResponse;
import com.unichristus.libraryapi.dto.response.ReadingResponse;
import com.unichristus.libraryapi.model.Reading;
import com.unichristus.libraryapi.service.ReadingService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReadingResponseMapper {

    public static ReadingResponse toReadingResponse(Reading reading,  boolean favorite) {
        return ReadingResponse.builder()
                .id(reading.getId())
                .book(new BookLowResponse(reading.getBook().getId(), reading.getBook().getTitle(), favorite))
                .status(reading.getStatus())
                .currentPage(reading.getCurrentPage())
                .progress(ReadingService.calculateProgressPercentage(reading))
                .startedAt(reading.getStartedAt())
                .lastReadedAt(reading.getLastReadedAt())
                .finishedAt(reading.getFinishedAt())
                .build();
    }

}
