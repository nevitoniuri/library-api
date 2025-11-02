package com.unichristus.libraryapi.domain.reading;

import com.unichristus.libraryapi.domain.book.Book;
import com.unichristus.libraryapi.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadingRepository extends JpaRepository<Reading, UUID> {

    @Query("""
            SELECT r FROM Reading r
            WHERE r.user.id = :userid
            ORDER BY r.lastReadedAt DESC
            """)
    List<Reading> findReadingsByUserOrderByLastReadedAtDesc(UUID userid);

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

    @Query("""
            SELECT r
            FROM Reading r
            WHERE r.user.id = :userId
              AND r.book = :book
              AND r.status = :status
            """)
    Optional<Reading> findReadingWithStatus(
            @Param("userId") UUID userId,
            @Param("book") Book book,
            @Param("status") ReadingStatus status
    );

}
