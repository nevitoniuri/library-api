package com.unichristus.libraryapi.controller;

import com.unichristus.libraryapi.dto.request.FavoriteRequestDTO;
import com.unichristus.libraryapi.dto.response.FavoriteResponseDTO;
import com.unichristus.libraryapi.service.FavoriteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping("/user/{userId}")
    public List<FavoriteResponseDTO> getFavoritesByUser(@PathVariable UUID userId) {
        return favoriteService.findAllByUserId(userId);
    }

    @PostMapping
    public FavoriteResponseDTO addFavorite(@RequestBody @Valid FavoriteRequestDTO favoriteRequestDTO) {
        return favoriteService.create(favoriteRequestDTO);
    }

    @DeleteMapping("/{favoriteId}")
    public void removeFavoriteById(@PathVariable UUID favoriteId) {
        favoriteService.delete(favoriteId);
    }

    // Endpoint alternativo para remover usando IDs de usuário e livro
    @DeleteMapping
    public void removeFavoriteByUserAndBook(
            @RequestParam UUID userId,
            @RequestParam UUID bookId) {
        favoriteService.deleteByUserAndBook(userId, bookId);
    }
}
