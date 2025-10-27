package com.unichristus.libraryapi.service;

import com.unichristus.libraryapi.exception.ServiceError;
import com.unichristus.libraryapi.exception.ServiceException;
import com.unichristus.libraryapi.model.Book;
import com.unichristus.libraryapi.model.Favorite;
import com.unichristus.libraryapi.model.User;
import com.unichristus.libraryapi.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final BookService bookService;

    public Page<Favorite> findAll(Pageable pageable) {
        return favoriteRepository.findAll(pageable);
    }

    public List<Favorite> findFavoritesByUser(UUID userId) {
        return favoriteRepository.findAllByUserId(userId);
    }

    public boolean isFavorite(UUID bookId, UUID userId) {
        Book book = bookService.findBookByIdOrThrow(bookId);
        return isFavorite(book, User.builder().id(userId).build());
    }

    public boolean isFavorite(Book book, User user) {
        return favoriteRepository.existsByUserAndBook(user, book);
    }

    public void favoriteBook(UUID bookId, UUID userId) {
        Book book = bookService.findBookByIdOrThrow(bookId);
        User user = User.builder().id(userId).build();
        if (isFavorite(book, user)) {
            throw new ServiceException(ServiceError.FAVORITE_ALREADY_EXISTS);
        }

        Favorite favorite = Favorite.builder()
                .user(user)
                .book(book)
                .build();

        favoriteRepository.save(favorite);
    }

    public void unfavoriteBook(UUID bookId, UUID userId) {
        Book book = bookService.findBookByIdOrThrow(bookId);
        favoriteRepository.deleteByUserIdAndBook(userId, book);
    }
}