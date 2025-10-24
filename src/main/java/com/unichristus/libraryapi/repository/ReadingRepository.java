package com.unichristus.libraryapi.repository;

import com.unichristus.libraryapi.enums.ReadingStatus;
import com.unichristus.libraryapi.model.Book;
import com.unichristus.libraryapi.model.Reading;
import com.unichristus.libraryapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadingRepository extends JpaRepository<Reading, UUID> {

    @Query("""
            SELECT r FROM Reading r
            WHERE r.user = :user
            ORDER BY r.lastReadedAt DESC
            """)
    List<Reading> findReadingsByUserOrderByLastReadedAtDesc(User user);

    @Query("""
            SELECT COUNT(r) > 0
            FROM Reading r
            WHERE r.user = :user
              AND r.book = :book
              AND r.status = :status
            """)
    boolean hasReadingWithStatus(
            @Param("user") User user,
            @Param("book") Book book,
            @Param("status") ReadingStatus status
    );

    @Query("SELECT r FROM Reading r WHERE r.user.id = :userId " +
            "AND r.book.id = :bookId ORDER BY r.startedAt DESC")
    List<Reading> findReadingHistory(
            @Param("userId") UUID userId,
            @Param("bookId") UUID bookId
    );

    Optional<Reading> findReadingByBookAndUserAndStatus(
            Book book,
            User user,
            ReadingStatus status
    );

    @Query("SELECT r FROM Reading r WHERE r.user.id = :userId " +
            "AND r.finishedAt IS NULL ORDER BY r.lastReadedAt DESC")
    List<Reading> findActiveReadingsByUser(@Param("userId") UUID userId);

}
