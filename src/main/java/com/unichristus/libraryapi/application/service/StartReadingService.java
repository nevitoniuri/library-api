package com.unichristus.libraryapi.application.service;

import com.unichristus.libraryapi.application.dto.response.StartReadingResponse;
import com.unichristus.libraryapi.application.mapper.ReadingResponseMapper;
import com.unichristus.libraryapi.domain.book.Book;
import com.unichristus.libraryapi.domain.book.BookService;
import com.unichristus.libraryapi.domain.favorite.FavoriteService;
import com.unichristus.libraryapi.domain.reading.Reading;
import com.unichristus.libraryapi.domain.reading.ReadingService;
import com.unichristus.libraryapi.domain.user.User;
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
        Reading savedReading = readingService.startReading(bookId, userId);
        Book book = savedReading.getBook();
        User user = savedReading.getUser();

        boolean isFavorite = favoriteService.isFavorite(book, user);
        String bookPdfUrl = bookService.getBookPdfUrl(book);

        return ReadingResponseMapper.toStartReadingResponse(savedReading, isFavorite, bookPdfUrl);
    }
}
