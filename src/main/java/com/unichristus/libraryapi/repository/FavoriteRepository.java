package com.unichristus.libraryapi.repository;

import com.unichristus.libraryapi.model.Book;
import com.unichristus.libraryapi.model.Favorite;
import com.unichristus.libraryapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {
    List<Favorite> findAllByUserId(UUID userId);

    boolean existsByUserAndBook(User user, Book book);

    void deleteByUserIdAndBook(UUID userId, Book book);
}