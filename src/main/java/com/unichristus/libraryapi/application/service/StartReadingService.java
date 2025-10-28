package com.unichristus.libraryapi.application.service;

import com.unichristus.libraryapi.application.dto.response.StartReadingResponse;
import com.unichristus.libraryapi.application.mapper.ReadingResponseMapper;
import com.unichristus.libraryapi.domain.book.BookService;
import com.unichristus.libraryapi.domain.favorite.FavoriteService;
import com.unichristus.libraryapi.domain.reading.Reading;
import com.unichristus.libraryapi.domain.reading.ReadingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StartReadingService {

    private final ReadingService readingService;
    private final BookService bookService;
    private final FavoriteService favoriteService;

    public StartReadingResponse startReading(UUID bookId, UUID userId) {
        Reading reading = readingService.startReading(bookId, userId);
        boolean isFavorite = favoriteService.isFavorite(reading.getBook(), reading.getUser());
        String bookPdfUrl = bookService.getBookPdfUrl(reading.getBook());
        return ReadingResponseMapper.toStartReadingResponse(reading, isFavorite, bookPdfUrl);
    }
}
