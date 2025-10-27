package com.unichristus.libraryapi.repository;

import com.unichristus.libraryapi.model.Book;
import com.unichristus.libraryapi.model.Favorite;
import com.unichristus.libraryapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {
    List<Favorite> findAllByUserId(UUID userId);
    List<Favorite> findAllByUser(User user);
    Page<Favorite> findAllByUser(User user, Pageable pageable);
    Optional<Favorite> findByUserAndBook(User user, Book book);
    boolean existsByUserAndBook(User user, Book book);
    void deleteByUserIdAndBook(UUID userId, Book book);
}