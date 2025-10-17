package com.unichristus.libraryapi.service;

import com.unichristus.libraryapi.enums.ReadingStatus;
import com.unichristus.libraryapi.exception.ServiceError;
import com.unichristus.libraryapi.exception.ServiceException;
import com.unichristus.libraryapi.model.Book;
import com.unichristus.libraryapi.model.Reading;
import com.unichristus.libraryapi.model.User;
import com.unichristus.libraryapi.repository.ReadingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadingService {

    private final ReadingRepository readingRepository;
    private final BookService bookService;
    private final UserService userService;

    public List<Reading> getReadingsByUser(UUID userId) {
        User user = userService.findUserByIdOrThrow(userId);
        return readingRepository.findReadingsByUser(user);
    }

    public Reading startReading(UUID bookId, UUID userId) {
        User user = userService.findUserByIdOrThrow(userId);
        Book book = bookService.findBookByIdOrThrow(bookId);
        LocalDateTime now = LocalDateTime.now();
        if (readingRepository.existsReadingByUserAndBook(user, book)) {
            throw new ServiceException(ServiceError.READING_ALREADY_EXISTS, userId, bookId);
        }
        Reading reading = Reading.builder()
                .book(book)
                .user(user)
                .status(ReadingStatus.IN_PROGRESS)
                .currentPage(1)
                .startedAt(now)
                .lastReadedAt(now)
                .build();
        return readingRepository.save(reading);
    }

    public Double calculatePercentRead(Reading reading) {
        Integer totalPages = reading.getBook().getNumberOfPages();
        Integer currentPage = reading.getCurrentPage();
        if (totalPages == null || totalPages == 0) {
            return 0.0;
        }
        return (currentPage.doubleValue() / totalPages.doubleValue()) * 100;
    }

}
