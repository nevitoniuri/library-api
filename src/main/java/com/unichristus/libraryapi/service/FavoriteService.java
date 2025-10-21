package com.unichristus.libraryapi.service;

import com.unichristus.libraryapi.dto.request.FavoriteCreateRequestDTO;
import com.unichristus.libraryapi.dto.request.FavoriteUpdateRequestDTO;
import com.unichristus.libraryapi.exception.ServiceError;
import com.unichristus.libraryapi.exception.ServiceException;
import com.unichristus.libraryapi.model.Book;
import com.unichristus.libraryapi.model.Favorite;
import com.unichristus.libraryapi.model.User;
import com.unichristus.libraryapi.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserService userService;
    private final BookService bookService;

    public List<Favorite> findAll() {
        return favoriteRepository.findAll();
    }

    public Favorite findFavoriteById(UUID id) {
        return favoriteRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceError.FAVORITE_NOT_FOUND, id));
    }

    public List<Favorite> findFavoritesByUserId(UUID userId) {
        User user = userService.findUserByIdOrThrow(userId);
        return favoriteRepository.findAllByUser(user);
    }

    public Favorite createFavorite(FavoriteCreateRequestDTO dto) {
        User user = userService.findUserByIdOrThrow(dto.userId());
        Book book = bookService.findBookByIdOrThrow(dto.bookId());

        // Verifica se já existe um favorito com este usuário e livro
        if (favoriteRepository.existsByUserAndBook(user, book)) {
            throw new ServiceException(ServiceError.FAVORITE_ALREADY_EXISTS,
                    "Usuário já adicionou este livro aos favoritos");
        }

        Favorite favorite = Favorite.builder()
                .user(user)
                .book(book)
                .build();

        return favoriteRepository.save(favorite);
    }

    public void updateFavorite(UUID id, FavoriteUpdateRequestDTO dto) {
        Favorite favorite = findFavoriteById(id);
        User user = userService.findUserByIdOrThrow(dto.userId());
        Book book = bookService.findBookByIdOrThrow(dto.bookId());

        // Verifica se já existe outro favorito com este usuário e livro, excluindo o atual
        if (favoriteRepository.existsByUserAndBookAndIdNot(user, book, id)) {
            throw new ServiceException(ServiceError.FAVORITE_ALREADY_EXISTS,
                    "Já existe um favorito com estes usuário e livro");
        }

        // Atualiza apenas se houver mudanças
        if (!favorite.getUser().equals(user) || !favorite.getBook().equals(book)) {
            favorite.setUser(user);
            favorite.setBook(book);
            favoriteRepository.save(favorite);
        }
    }

    public void deleteFavorite(UUID id) {
        favoriteRepository.delete(findFavoriteById(id));
    }

    public void deleteByUserAndBook(UUID userId, UUID bookId) {
        User user = userService.findUserByIdOrThrow(userId);
        Book book = bookService.findBookByIdOrThrow(bookId);
        favoriteRepository.deleteByUserAndBook(user, book);
    }
}