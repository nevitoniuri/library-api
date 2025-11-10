package com.unichristus.libraryapi.domain.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    @Query("""
            SELECT new com.unichristus.libraryapi.domain.review.BookAverageScore(
                r.book.id,
                AVG(CAST(r.rating AS double)),
                COUNT(r)
            )
            FROM Review r
            WHERE r.book.id IN :bookIds
            GROUP BY r.book.id, r.book.title
            """)
    List<BookAverageScore> findAverageScoresByBookIds(@Param("bookIds") List<UUID> bookIds);

    @Query("""
            SELECT new com.unichristus.libraryapi.domain.review.BookAverageScore(
                r.book.id,
                AVG(CAST(r.rating AS double)),
                COUNT(r)
            )
            FROM Review r
            WHERE r.book.id = :bookId
            GROUP BY r.book.id, r.book.title
            """)
    Optional<BookAverageScore> findAverageScoreByBookId(@Param("bookId") UUID bookId);

    Optional<Review> findByUserIdAndBookId(UUID userId, UUID bookId);

    Page<Review> findByUserId(UUID userId, Pageable pageable);
}
