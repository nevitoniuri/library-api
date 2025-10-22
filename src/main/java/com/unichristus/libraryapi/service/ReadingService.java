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

    public Reading findReadingByIdOrThrow(UUID readingId) {
        return readingRepository.findById(readingId)
                .orElseThrow(() -> new ServiceException(ServiceError.READING_NOT_FOUND, readingId));
    }

    public List<Reading> getRecentReadingsByUser(User user, int limit) {
        return readingRepository.findReadingsByUserOrderByLastReadedAtDesc(user, limit);
    }

    public boolean hasInProgressReading(User user, Book book) {
        return readingRepository.hasReadingWithStatus(user, book, ReadingStatus.IN_PROGRESS);
    }

    public Reading startReading(UUID bookId, User user) {
        Book book = bookService.findBookByIdOrThrow(bookId);
        LocalDateTime now = LocalDateTime.now();
        if (hasInProgressReading(user, book)) {
            throw new ServiceException(ServiceError.READING_IN_PROGRESS, user.getEmail(), book.getTitle());
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

    public void updateReadingProgress(UUID readingId, UUID userId, Integer newCurrentPage) {
        Reading reading = findReadingByIdOrThrow(readingId);
        if (!userId.equals(reading.getUser().getId())) {
            throw new ServiceException(ServiceError.READING_USER_MISMATCH, userId, readingId);
        }
        if (reading.getStatus() == ReadingStatus.FINISHED) {
            throw new ServiceException(ServiceError.READING_FINISHED, reading.getId());
        }
        if (newCurrentPage < reading.getCurrentPage()) {
            throw new ServiceException(ServiceError.READING_INVALID_PAGE_PROGRESS, reading.getCurrentPage());
        }
        Integer totalPages = reading.getBook().getNumberOfPages();
        LocalDateTime now = LocalDateTime.now();
        if (newCurrentPage >= totalPages) {
            reading.setCurrentPage(totalPages);
            reading.setStatus(ReadingStatus.FINISHED);
            reading.setFinishedAt(now);
        } else {
            reading.setCurrentPage(newCurrentPage);
        }
        reading.setLastReadedAt(now);
        readingRepository.save(reading);
    }

    public void finishReading(UUID readingId) {
        Reading reading = findReadingByIdOrThrow(readingId);
        if (reading.getStatus() == ReadingStatus.FINISHED) {
            throw new ServiceException(ServiceError.READING_FINISHED, reading.getId());
        }
        reading.setStatus(ReadingStatus.FINISHED);
        reading.setFinishedAt(LocalDateTime.now());
        reading.setCurrentPage(reading.getBook().getNumberOfPages());
        readingRepository.save(reading);
    }

    public int calculateProgressPercentage(Reading reading) {
        Integer totalPages = reading.getBook().getNumberOfPages();
        Integer currentPage = reading.getCurrentPage();
        if (totalPages == null || totalPages == 0 || currentPage == null) {
            return 0;
        }
        double percentage = (currentPage.doubleValue() / totalPages.doubleValue()) * 100;
        return (int) Math.round(percentage);
    }

}
