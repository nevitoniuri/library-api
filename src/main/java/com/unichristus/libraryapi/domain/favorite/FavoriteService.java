package com.unichristus.libraryapi.domain.favorite;

import com.unichristus.libraryapi.domain.book.Book;
import com.unichristus.libraryapi.domain.favorite.exception.FavoriteAlreadyExistsException;
import com.unichristus.libraryapi.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public List<Favorite> findAll(int page, int size) {
        return favoriteRepository.findAll(page, size);
    }

    public List<Favorite> findFavoritesByUser(UUID userId) {
        return favoriteRepository.findAllByUserId(userId);
    }

    public boolean isFavorite(Book book, User user) {
        return favoriteRepository.existsByUserAndBook(user, book);
    }

    public void createFavorite(Book book, User user) {
        if (isFavorite(book, user)) {
            throw new FavoriteAlreadyExistsException(user.getId(), book.getId());
        }
        Favorite favorite = Favorite.builder()
                .user(user)
                .book(book)
                .build();

        favoriteRepository.save(favorite);
    }

    public void unfavoriteBook(Book book, UUID userId) {
        favoriteRepository.deleteByUserIdAndBook(userId, book);
    }
}