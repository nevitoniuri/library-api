package com.unichristus.libraryapi.application.mapper;

import com.unichristus.libraryapi.application.dto.response.BookAverageScoreResponse;
import com.unichristus.libraryapi.application.dto.response.ReviewResponse;
import com.unichristus.libraryapi.domain.review.BookAverageScore;
import com.unichristus.libraryapi.domain.review.Review;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReviewResponseMapper {

    public static ReviewResponse toReviewResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .bookId(review.getBook().getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }

    public static BookAverageScoreResponse toBookAverageScoreResponse(BookAverageScore bookAverageScore) {
        return new BookAverageScoreResponse(
                bookAverageScore.bookId(),
                bookAverageScore.averageRating(),
                bookAverageScore.totalReviews()
        );
    }
}
