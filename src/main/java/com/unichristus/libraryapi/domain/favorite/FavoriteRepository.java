package com.unichristus.libraryapi.domain.favorite;

import com.unichristus.libraryapi.domain.book.Book;
import com.unichristus.libraryapi.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface FavoriteRepository {

    void save(Favorite favorite);

    Page<Favorite> findAll(Pageable pageable);

    List<Favorite> findAllByUserId(UUID userId);

    boolean existsByUserAndBook(User user, Book book);

    void deleteByUserIdAndBook(UUID userId, Book book);
}