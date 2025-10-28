package com.unichristus.libraryapi.infrastructure.persistence.favorite;

import com.unichristus.libraryapi.domain.book.Book;
import com.unichristus.libraryapi.domain.favorite.Favorite;
import com.unichristus.libraryapi.domain.favorite.FavoriteRepository;
import com.unichristus.libraryapi.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaFavoriteRepositoryAdapter implements FavoriteRepository {

    private final JpaFavoriteRepository jpaFavoriteRepository;

    @Override
    public void save(Favorite favorite) {
        jpaFavoriteRepository.save(favorite);
    }

    @Override
    public List<Favorite> findAll(int page, int size) {
        return jpaFavoriteRepository.findAll(PageRequest.of(page, size)).toList();
    }

    @Override
    public List<Favorite> findAllByUserId(UUID userId) {
        return jpaFavoriteRepository.findAllByUserId(userId);
    }

    @Override
    public boolean existsByUserAndBook(User user, Book book) {
        return false;
    }

    @Override
    public void deleteByUserIdAndBook(UUID userId, Book book) {

    }
}
