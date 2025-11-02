package com.unichristus.libraryapi.domain.reading;

import com.unichristus.libraryapi.domain.book.Book;
import com.unichristus.libraryapi.domain.book.BookService;
import com.unichristus.libraryapi.domain.reading.exception.*;
import com.unichristus.libraryapi.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadingService {

    private final ReadingRepository readingRepository;
    private final BookService bookService;

    public Reading findByIdOrThrow(UUID readingId) throws ReadingNotFoundException {
        return readingRepository.findById(readingId)
                .orElseThrow(() -> new ReadingNotFoundException(readingId));
    }

    public List<Reading> findReadingsByUser(UUID userId) {
        return readingRepository.findReadingsByUserOrderByLastReadedAtDesc(userId);
    }

    public boolean hasInProgressReading(User user, Book book) {
        return readingRepository.hasReadingWithStatus(user, book, ReadingStatus.IN_PROGRESS);
    }

    public Optional<Reading> findReadindInProgress(UUID userId, Book book) {
        return readingRepository.findReadingWithStatus(userId, book, ReadingStatus.IN_PROGRESS);
    }

    public Reading findReadingInProgressOrCreateReading(UUID userId, UUID bookId) {
        Book book = bookService.findBookByIdOrThrow(bookId);
        return findReadindInProgress(userId, book)
                .orElseGet(() -> createReading(userId, book));
    }

    private Reading createReading(UUID userId, Book book) throws PdfNotAvailableException {
        if (!book.isHasPdf()) {
            throw new PdfNotAvailableException(book.getId());
        }
        LocalDateTime now = LocalDateTime.now();
        User user = User.builder().id(userId).build();
        if (hasInProgressReading(user, book)) {
            throw new ReadingInProgressException(userId, book.getId());
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

    public void updateReadingProgress(Reading reading, Integer newCurrentPage) {
        if (reading.getStatus() == ReadingStatus.FINISHED) {
            throw new ReadingAlreadyFinishedException(reading.getId());
        }
        if (newCurrentPage < reading.getCurrentPage() || newCurrentPage > reading.getBook().getNumberOfPages()) {
            throw new InvalidPageProgressException();
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
