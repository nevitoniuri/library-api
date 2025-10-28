package com.unichristus.libraryapi.application.mapper;

import com.unichristus.libraryapi.application.dto.response.BookLowResponse;
import com.unichristus.libraryapi.application.dto.response.ReadingResponse;
import com.unichristus.libraryapi.application.dto.response.StartReadingResponse;
import com.unichristus.libraryapi.domain.reading.Reading;
import com.unichristus.libraryapi.domain.reading.ReadingService;
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

    public static StartReadingResponse toStartReadingResponse(Reading reading, boolean favorite, String bookPdfUrl) {
        return StartReadingResponse.builder()
                .id(reading.getId())
                .book(new BookLowResponse(reading.getBook().getId(), reading.getBook().getTitle(), favorite))
                .status(reading.getStatus())
                .currentPage(reading.getCurrentPage())
                .progress(ReadingService.calculateProgressPercentage(reading))
                .startedAt(reading.getStartedAt())
                .lastReadedAt(reading.getLastReadedAt())
                .finishedAt(reading.getFinishedAt())
                .bookPdfUrl(bookPdfUrl)
                .build();
    }

}
