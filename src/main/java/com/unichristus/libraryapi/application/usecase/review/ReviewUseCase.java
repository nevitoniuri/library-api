package com.unichristus.libraryapi.application.usecase.review;

import com.unichristus.libraryapi.application.annotation.UseCase;
import com.unichristus.libraryapi.application.dto.request.ReviewCreateRequest;
import com.unichristus.libraryapi.application.dto.request.ReviewUpdateRequest;
import com.unichristus.libraryapi.application.dto.response.BookAverageScoreResponse;
import com.unichristus.libraryapi.application.dto.response.ReviewResponse;
import com.unichristus.libraryapi.application.mapper.ReviewResponseMapper;
import com.unichristus.libraryapi.domain.book.Book;
import com.unichristus.libraryapi.domain.book.BookService;
import com.unichristus.libraryapi.domain.reading.Reading;
import com.unichristus.libraryapi.domain.reading.ReadingService;
import com.unichristus.libraryapi.domain.review.Review;
import com.unichristus.libraryapi.domain.review.ReviewService;
import com.unichristus.libraryapi.domain.review.exception.ReviewAlreadyExists;
import com.unichristus.libraryapi.domain.review.exception.ReviewBelongsToAnotherUserException;
import com.unichristus.libraryapi.domain.review.exception.ReviewNotAllowedException;
import com.unichristus.libraryapi.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class ReviewUseCase {

    private final ReviewService reviewService;
    private final ReadingService readingService;
    private final BookService bookService;

    public ReviewResponse getReviewById(UUID reviewId) {
        Review review = reviewService.findByIdOrThrow(reviewId);
        return ReviewResponseMapper.toReviewResponse(review);
    }

    public ReviewResponse createReview(UUID userId, ReviewCreateRequest request) {
        Book book = bookService.findBookByIdOrThrow(request.bookId());
        Reading reading = readingService.findInProgressReadingByUserAndBook(userId, book)
                .orElseThrow(ReviewNotAllowedException::new);
        reviewService.findByUserAndBookId(userId, book.getId()).ifPresent(r -> {
            throw new ReviewAlreadyExists();
        });
        Review review = Review.builder()
                .book(book)
                .user(User.builder().id(userId).build())
                .rating(request.rating())
                .progress(ReadingService.calculateProgressPercentage(reading))
                .comment(request.comment())
                .build();
        return ReviewResponseMapper.toReviewResponse(reviewService.save(review));
    }

    public ReviewResponse updateReview(UUID reviewId, UUID userId, ReviewUpdateRequest request) {
        Review existingReview = reviewService.findByIdOrThrow(reviewId);
        checkUser(userId, existingReview);
        existingReview.setRating(request.rating());
        existingReview.setComment(request.comment().trim());
        Review updatedReview = reviewService.save(existingReview);
        return ReviewResponseMapper.toReviewResponse(updatedReview);
    }

    private static void checkUser(UUID userId, Review existingReview) {
        if (!existingReview.getUser().getId().equals(userId)) {
            throw new ReviewBelongsToAnotherUserException();
        }
    }

    public void deleteReview(UUID reviewId, UUID userId) {
        Review existingReview = reviewService.findByIdOrThrow(reviewId);
        checkUser(userId, existingReview);
        reviewService.deleteById(reviewId);
    }

    public Page<ReviewResponse> getAllReviews(UUID userId, Pageable pageable) {
        Page<Review> reviews;

        if (userId != null) {
            reviews = reviewService.findByUserId(userId, pageable);
        } else {
            reviews = reviewService.findAll(pageable);
        }

        return reviews.map(ReviewResponseMapper::toReviewResponse);
    }

    public List<BookAverageScoreResponse> getAverageScores(List<UUID> bookIds) {
        return reviewService.findAverageScoresByBookIds(bookIds).stream()
                .map(ReviewResponseMapper::toBookAverageScoreResponse)
                .toList();
    }
}
