package com.unichristus.libraryapi.infrastructure.persistence.favorite;

import com.unichristus.libraryapi.domain.book.Book;
import com.unichristus.libraryapi.domain.favorite.Favorite;
import com.unichristus.libraryapi.domain.favorite.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<Favorite> findAll(Pageable pageable) {
        return jpaFavoriteRepository.findAll(pageable);
    }

    @Override
    public List<Favorite> findAllByUserId(UUID userId) {
        return jpaFavoriteRepository.findAllByUserId(userId);
    }

    @Override
    public boolean existsByUserAndBook(UUID userId, Book book) {
        return jpaFavoriteRepository.existsByUserIdAndBook(userId, book);
    }

    @Override
    public void deleteByUserIdAndBook(UUID userId, Book book) {
        jpaFavoriteRepository.deleteByUserIdAndBook(userId, book);
    }
}
