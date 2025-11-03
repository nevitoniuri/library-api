package com.unichristus.libraryapi.infrastructure.persistence.review;

import com.unichristus.libraryapi.domain.review.BookAverageScore;
import com.unichristus.libraryapi.domain.review.Review;
import com.unichristus.libraryapi.domain.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ReviewJpaRepositoryAdapter implements ReviewRepository {

    private final ReviewJpaRepository reviewJpaRepository;

    @Override
    public Optional<Review> findById(UUID reviewId) {
        return reviewJpaRepository.findById(reviewId);
    }

    @Override
    public Review save(Review review) {
        return reviewJpaRepository.save(review);
    }

    @Override
    public void deleteById(UUID reviewId) {
        reviewJpaRepository.deleteById(reviewId);
    }

    @Override
    public Page<Review> findByUserId(UUID userId, Pageable pageable) {
        return reviewJpaRepository.findReviewByUserId(userId, pageable);
    }

    @Override
    public Page<Review> findAll(Pageable pageable) {
        pageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
        return reviewJpaRepository.findAll(pageable);
    }

    @Override
    public List<BookAverageScore> findAverageScoresByBookIds(List<UUID> bookIds) {
        return reviewJpaRepository.findAverageScoresByBookIds(bookIds);
    }

    @Override
    public Optional<BookAverageScore> findAverageScoresByBookId(UUID bookId) {
        return reviewJpaRepository.findAverageScoreByBookId(bookId);
    }

    @Override
    public Optional<Review> findByUserIdAndBookId(UUID userId, UUID bookId) {
        return reviewJpaRepository.findByUserIdAndBookId(userId, bookId);
    }
}
