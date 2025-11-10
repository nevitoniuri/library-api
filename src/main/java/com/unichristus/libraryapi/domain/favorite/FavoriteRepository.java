package com.unichristus.libraryapi.domain.favorite;

import com.unichristus.libraryapi.domain.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {

    List<Favorite> findAllByUserId(UUID userId);

    boolean existsByUserIdAndBook(UUID userId, Book book);

    void deleteByUserIdAndBook(UUID userId, Book book);
}