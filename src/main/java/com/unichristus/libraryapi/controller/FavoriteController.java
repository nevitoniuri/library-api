package com.unichristus.libraryapi.controller;

import com.unichristus.libraryapi.dto.request.FavoriteCreateRequestDTO;
import com.unichristus.libraryapi.dto.request.FavoriteUpdateRequestDTO;
import com.unichristus.libraryapi.dto.response.FavoriteResponseDTO;
import com.unichristus.libraryapi.service.FavoriteService;
import com.unichristus.libraryapi.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@RestController
@RequiredArgsConstructor
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FavoriteResponseDTO createFavorite(@RequestBody @Valid FavoriteCreateRequestDTO dto) {
        return MapperUtil.parse(favoriteService.createFavorite(dto), FavoriteResponseDTO.class);
    }

    @GetMapping("/{id}")
    public FavoriteResponseDTO getFavoriteById(@PathVariable UUID id) {
        return MapperUtil.parse(favoriteService.findFavoriteById(id), FavoriteResponseDTO.class);
    }

    @GetMapping
    public List<FavoriteResponseDTO> getAllFavorites() {
        return favoriteService.findAll().stream()
                .map(favorite -> MapperUtil.parse(favorite, FavoriteResponseDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/user/{userId}")
    public List<FavoriteResponseDTO> getFavoritesByUser(@PathVariable UUID userId) {
        return favoriteService.findFavoritesByUserId(userId).stream()
                .map(favorite -> MapperUtil.parse(favorite, FavoriteResponseDTO.class))
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateFavorite(@PathVariable UUID id, @RequestBody @Valid FavoriteUpdateRequestDTO dto) {
        favoriteService.updateFavorite(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFavorite(@PathVariable UUID id) {
        favoriteService.deleteFavorite(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByUserAndBook(
            @RequestParam UUID userId,
            @RequestParam UUID bookId) {
        favoriteService.deleteByUserAndBook(userId, bookId);
    }
}