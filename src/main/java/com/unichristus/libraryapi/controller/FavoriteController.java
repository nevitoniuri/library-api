package com.unichristus.libraryapi.controller;

import com.unichristus.libraryapi.annotation.LoggedUser;
import com.unichristus.libraryapi.dto.request.FavoriteBookRequest;
import com.unichristus.libraryapi.dto.response.FavoriteResponse;
import com.unichristus.libraryapi.security.CustomUserDetails;
import com.unichristus.libraryapi.service.FavoriteService;
import com.unichristus.libraryapi.util.ServiceURIs;
import com.unichristus.libraryapi.mapper.FavoriteResponseMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceURIs.USERS_RESOURCE + "/me/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping
    @Operation(summary = "Listar favoritos do usuário logado")
    public Page<FavoriteResponse> getUserFavorites(@LoggedUser CustomUserDetails userDetails, Pageable pageable) {
        return favoriteService.findFavoritesByUser(userDetails.toEntityReference(), pageable)
                .map(FavoriteResponseMapper::toFavoriteResponse);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Favoritar livro")
    public void favoriteBook(
            @RequestBody @Valid FavoriteBookRequest request,
            @LoggedUser CustomUserDetails userDetails
    ) {
        favoriteService.favoriteBook(request.bookId(), userDetails.toEntityReference());
    }

    @GetMapping("/{bookId}")
    @Operation(summary = "Verificar se livro é favorito")
    public boolean isFavorite(
            @PathVariable UUID bookId,
            @LoggedUser CustomUserDetails userDetails
    ) {
        return favoriteService.isFavorite(bookId, userDetails.toEntityReference());
    }

    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remover livro dos favoritos")
    public void unfavoriteBook(
            @PathVariable UUID bookId,
            @LoggedUser CustomUserDetails userDetails
    ) {
        favoriteService.unfavoriteBook(bookId, userDetails.toEntityReference());
    }
}