package com.unichristus.libraryapi.domain.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository {

    Optional<Review> findById(UUID reviewId);

    Review save(Review review);

    void deleteById(UUID reviewId);

    Page<Review> findByUserId(UUID userId, Pageable pageable);

    Page<Review> findAll(Pageable pageable);

    List<BookAverageScore> findAverageScoresByBookIds(List<UUID> bookIds);

    Optional<BookAverageScore> findAverageScoresByBookId(UUID bookId);

    Optional<Review> findByUserIdAndBookId(UUID userId, UUID bookId);
}
