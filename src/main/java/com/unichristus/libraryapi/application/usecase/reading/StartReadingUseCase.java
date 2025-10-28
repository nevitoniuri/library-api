package com.unichristus.libraryapi.application.usecase.reading;

import com.unichristus.libraryapi.application.dto.response.StartReadingResponse;
import com.unichristus.libraryapi.application.mapper.ReadingResponseMapper;
import com.unichristus.libraryapi.application.usecase.book.BookPdfUseCase;
import com.unichristus.libraryapi.domain.book.Book;
import com.unichristus.libraryapi.domain.favorite.FavoriteService;
import com.unichristus.libraryapi.domain.reading.Reading;
import com.unichristus.libraryapi.domain.reading.ReadingService;
import com.unichristus.libraryapi.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StartReadingUseCase {

    private final ReadingService readingService;
    private final FavoriteService favoriteService;
    private final BookPdfUseCase bookPdfUseCase;

    public StartReadingResponse startReading(UUID bookId, UUID userId) {
        Reading reading = readingService.createReading(bookId, userId);
        Book book = reading.getBook();
        User user = reading.getUser();
        boolean isFavorite = favoriteService.isFavorite(book, user);
        String bookPdfUrl = bookPdfUseCase.getBookPdfUrl(book);
        return ReadingResponseMapper.toStartReadingResponse(reading, isFavorite, bookPdfUrl);
    }
}
