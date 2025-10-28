package com.unichristus.libraryapi.application.usecase.favorite;

import com.unichristus.libraryapi.domain.book.Book;
import com.unichristus.libraryapi.domain.book.BookService;
import com.unichristus.libraryapi.domain.favorite.FavoriteService;
import com.unichristus.libraryapi.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavoriteBookUseCase {

    private final BookService bookService;
    private final FavoriteService favoriteService;

    public void favoriteBook(UUID bookId, UUID userId) {
        Book book = bookService.findBookByIdOrThrow(bookId);
        User user = User.builder().id(userId).build();
        favoriteService.createFavorite(book, user);
    }

    public void unfavoriteBook(UUID bookId, UUID userId) {
        Book book = bookService.findBookByIdOrThrow(bookId);
        favoriteService.unfavoriteBook(book, userId);
    }

    public boolean isFavorite(UUID bookId, UUID userId) {
        Book book = bookService.findBookByIdOrThrow(bookId);
        User user = User.builder().id(userId).build();
        return favoriteService.isFavorite(book, user);
    }
}
