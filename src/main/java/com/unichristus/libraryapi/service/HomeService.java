package com.unichristus.libraryapi.service;

import com.unichristus.libraryapi.dto.response.BookLowResponseDTO;
import com.unichristus.libraryapi.dto.response.HomeResponseDTO;
import com.unichristus.libraryapi.dto.response.ReadingResponseDTO;
import com.unichristus.libraryapi.enums.ReadingStatus;
import com.unichristus.libraryapi.model.Reading;
import com.unichristus.libraryapi.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HomeService {

    @Value("${app.recent-readings-limit}")
    private int recentReadingsLimit;

    private final ReadingService readingService;
    private final UserService userService;

    public HomeResponseDTO resume(UUID userId) {
        User user = userService.findUserByIdOrThrow(userId);
        List<Reading> readings = readingService.getRecentReadingsByUser(user, recentReadingsLimit);
        int totalBooksReading = readings.stream()
                .filter((reading) -> reading.getStatus().equals(ReadingStatus.IN_PROGRESS))
                .toList().size();
        List<Reading> finishedReadings = readings.stream()
                .filter((reading) -> reading.getStatus().equals(ReadingStatus.FINISHED)).toList();
        int totalBooksRead = finishedReadings.size();
        int totalPagesRead = finishedReadings.stream()
                .mapToInt(Reading::getCurrentPage)
                .sum();
        List<ReadingResponseDTO> readingsResponse = readings
                .stream().map((this::buildReadingResponse)).toList();
        return new HomeResponseDTO(readingsResponse, totalBooksReading, totalBooksRead, totalPagesRead);
    }

    private ReadingResponseDTO buildReadingResponse(Reading reading) {
        return ReadingResponseDTO.builder()
                .id(reading.getId())
                .book(new BookLowResponseDTO(reading.getBook().getId(), reading.getBook().getTitle()))
                .status(reading.getStatus())
                .currentPage(reading.getCurrentPage())
                .progress(readingService.calculateProgressPercentage(reading))
                .startedAt(reading.getStartedAt())
                .lastReadedAt(reading.getLastReadedAt())
                .finishedAt(reading.getFinishedAt())
                .build();
    }

}
