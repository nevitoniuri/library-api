package com.unichristus.libraryapi.service;

import com.unichristus.libraryapi.dto.request.FavoriteRequestDTO;
import com.unichristus.libraryapi.dto.response.FavoriteResponseDTO;
import com.unichristus.libraryapi.exception.ServiceError;
import com.unichristus.libraryapi.exception.ServiceException;
import com.unichristus.libraryapi.model.Book;
import com.unichristus.libraryapi.model.Favorite;
import com.unichristus.libraryapi.model.User;
import com.unichristus.libraryapi.repository.BookRepository;
import com.unichristus.libraryapi.repository.FavoriteRepository;
import com.unichristus.libraryapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;


    @Transactional(readOnly = true)
    public List<FavoriteResponseDTO> findAllByUserId(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ServiceError.USER_NOT_FOUND, userId));

        return favoriteRepository.findAllByUser(user).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public FavoriteResponseDTO create(FavoriteRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ServiceException(ServiceError.USER_NOT_FOUND, dto.getUserId()));

        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new ServiceException(ServiceError.BOOK_NOT_FOUND, dto.getBookId()));

        // Validação para evitar duplicatas
        if (favoriteRepository.existsByUserAndBook(user, book)) {
            throw new ServiceException(ServiceError.FAVORITE_ALREADY_EXISTS);
        }

        Favorite favorite = Favorite.builder()
                .user(user)
                .book(book)
                .build();

        Favorite savedFavorite = favoriteRepository.save(favorite);
        return toResponseDTO(savedFavorite);
    }

    @Transactional
    public void delete(UUID favoriteId) {
        if (!favoriteRepository.existsById(favoriteId)) {
            throw new ServiceException(ServiceError.FAVORITE_NOT_FOUND, favoriteId);
        }
        favoriteRepository.deleteById(favoriteId);
    }


    @Transactional
    public void deleteByUserAndBook(UUID userId, UUID bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ServiceError.USER_NOT_FOUND, userId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ServiceException(ServiceError.BOOK_NOT_FOUND, bookId));

        Favorite favorite = favoriteRepository.findByUserAndBook(user, book)
                .orElseThrow(() -> new ServiceException(ServiceError.FAVORITE_NOT_FOUND));

        favoriteRepository.delete(favorite);
    }


    private FavoriteResponseDTO toResponseDTO(Favorite favorite) {
        return FavoriteResponseDTO.builder()
                .id(favorite.getId())
                .userId(favorite.getUser().getId())
                .bookId(favorite.getBook().getId())
                .bookTitle(favorite.getBook().getTitle())
                .bookIsbn(favorite.getBook().getIsbn())
                .createdAt(favorite.getCreatedAt())
                .build();
    }
}