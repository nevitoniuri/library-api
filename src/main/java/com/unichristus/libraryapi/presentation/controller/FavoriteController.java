package com.unichristus.libraryapi.presentation.controller;

import com.unichristus.libraryapi.application.dto.request.FavoriteBookRequest;
import com.unichristus.libraryapi.application.dto.response.FavoriteResponse;
import com.unichristus.libraryapi.application.mapper.FavoriteResponseMapper;
import com.unichristus.libraryapi.application.util.ServiceURIs;
import com.unichristus.libraryapi.domain.favorite.FavoriteService;
import com.unichristus.libraryapi.infra.security.LoggedUser;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceURIs.USERS_RESOURCE + "/me/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping
    @Operation(summary = "Listar favoritos do usuário logado")
    public List<FavoriteResponse> getUserFavorites(@LoggedUser UUID userId) {
        return favoriteService.findFavoritesByUser(userId).stream()
                .map(FavoriteResponseMapper::toFavoriteResponse).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Favoritar livro")
    public void favoriteBook(
            @RequestBody @Valid FavoriteBookRequest request,
            @LoggedUser UUID userId
    ) {
        favoriteService.favoriteBook(request.bookId(), userId);
    }

    @GetMapping("/{bookId}")
    @Operation(summary = "Verificar se livro é favorito")
    public boolean isFavorite(
            @PathVariable UUID bookId,
            @LoggedUser UUID userId
    ) {
        return favoriteService.isFavorite(bookId, userId);
    }

    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remover livro dos favoritos")
    public void unfavoriteBook(
            @PathVariable UUID bookId,
            @LoggedUser UUID userId
    ) {
        favoriteService.unfavoriteBook(bookId, userId);
    }
}