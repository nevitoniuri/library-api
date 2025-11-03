package com.unichristus.libraryapi.domain.review;

import java.util.UUID;

public record BookAverageScore(
        UUID bookId,
        Double averageRating,
        Long totalReviews
) {
}
