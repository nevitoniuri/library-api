package com.unichristus.libraryapi.service;

import com.unichristus.libraryapi.mapper.ReadingResponseMapper;
import com.unichristus.libraryapi.dto.response.ReadingResponse;
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
    private final FavoriteService favoriteService;

    public Reading findByIdOrThrow(UUID readingId) throws ServiceException {
        return readingRepository.findById(readingId)
                .orElseThrow(() -> new ServiceException(ServiceError.READING_NOT_FOUND, readingId));
    }

    public List<Reading> findReadingsByUser(User user) {
        return readingRepository.findReadingsByUserOrderByLastReadedAtDesc(user);
    }

    public boolean hasInProgressReading(User user, Book book) {
        return readingRepository.hasReadingWithStatus(user, book, ReadingStatus.IN_PROGRESS);
    }

    public ReadingResponse startReading(UUID bookId, User user) {
        Book book = bookService.findBookByIdOrThrow(bookId);
        LocalDateTime now = LocalDateTime.now();
        if (hasInProgressReading(user, book)) {
            throw new ServiceException(ServiceError.READING_IN_PROGRESS_ALREADY, user.getEmail(), book.getTitle());
        }
        Reading reading = Reading.builder()
                .book(book)
                .user(user)
                .status(ReadingStatus.IN_PROGRESS)
                .currentPage(1)
                .startedAt(now)
                .lastReadedAt(now)
                .build();
        Reading saved = readingRepository.save(reading);
        return ReadingResponseMapper.toReadingResponse(saved, favoriteService.isFavorite(book, user));
    }

    public void updateReadingProgress(UUID readingId, User user, Integer newCurrentPage) {
        Reading reading = findByIdOrThrow(readingId);
        if (!user.getId().equals(reading.getUser().getId())) {
            throw new ServiceException(ServiceError.READING_BELONGS_TO_ANOTHER_USER, readingId);
        }
        if (reading.getStatus() == ReadingStatus.FINISHED) {
            throw new ServiceException(ServiceError.READING_FINISHED_ALREADY, readingId);
        }
        if (newCurrentPage < reading.getCurrentPage() || newCurrentPage > reading.getBook().getNumberOfPages()) {
            throw new ServiceException(ServiceError.READING_INVALID_PAGE_PROGRESS);
        }
        Integer totalPages = reading.getBook().getNumberOfPages();
        LocalDateTime now = LocalDateTime.now();
        if (newCurrentPage >= totalPages) {
            reading.setStatus(ReadingStatus.FINISHED);
            reading.setCurrentPage(totalPages);
            reading.setFinishedAt(now);
        } else {
            reading.setCurrentPage(newCurrentPage);
        }
        reading.setLastReadedAt(now);
        readingRepository.save(reading);
    }

    public static int calculateProgressPercentage(Reading reading) {
        Integer totalPages = reading.getBook().getNumberOfPages();
        Integer currentPage = reading.getCurrentPage();
        if (totalPages == null || totalPages == 0 || currentPage == null) {
            return 0;
        }
        double percentage = (currentPage.doubleValue() / totalPages.doubleValue()) * 100;
        return (int) Math.round(percentage);
    }

}
