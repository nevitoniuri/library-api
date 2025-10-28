package com.unichristus.libraryapi.domain.favorite;

import com.unichristus.libraryapi.domain.book.Book;
import com.unichristus.libraryapi.domain.user.User;

import java.util.List;
import java.util.UUID;

public interface FavoriteRepository {

    void save(Favorite favorite);

    List<Favorite> findAll(int page, int size);

    List<Favorite> findAllByUserId(UUID userId);

    boolean existsByUserAndBook(User user, Book book);

    void deleteByUserIdAndBook(UUID userId, Book book);
}