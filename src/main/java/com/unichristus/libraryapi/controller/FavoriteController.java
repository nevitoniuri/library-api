package com.unichristus.libraryapi.controller;

import com.unichristus.libraryapi.annotation.LoggedUser;
import com.unichristus.libraryapi.dto.request.FavoriteBookRequest;
import com.unichristus.libraryapi.dto.response.FavoriteResponse;
import com.unichristus.libraryapi.security.CustomUserDetails;
import com.unichristus.libraryapi.service.FavoriteService;
import com.unichristus.libraryapi.util.MapperUtil;
import com.unichristus.libraryapi.util.ServiceURIs;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceURIs.USERS_RESOURCE + "/me/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    //TODO: Corrigir endpoint, renomear para "getUserFavorites" e receber usuário logado.
    @GetMapping()
    public List<FavoriteResponse> getFavoritesByUser(@PathVariable UUID userId) {
        return favoriteService.findFavoritesByUserId(userId).stream()
                .map(favorite -> MapperUtil.parse(favorite, FavoriteResponse.class))
                .collect(Collectors.toList());
    }

    //TODO: Testar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Favoritar livro")
    public void favoriteBook(
            @RequestBody @Valid FavoriteBookRequest request,
            @LoggedUser CustomUserDetails userDetails
    ) {
        favoriteService.favoriteBook(request.bookId(), userDetails.toEntityReference());
    }

    //TODO: Testar
    @GetMapping("/{bookId}")
    @Operation(summary = "Verificar se livro é favorito")
    public boolean isFavorite(
            @PathVariable UUID bookId,
            @LoggedUser CustomUserDetails userDetails
    ) {
        return favoriteService.isFavorite(bookId, userDetails.toEntityReference());
    }

    //TODO: renomear para unfavoriteBook, receber bookId na url e receber usuario logado
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByUserAndBook(
            @RequestParam UUID userId,
            @RequestParam UUID bookId) {
        favoriteService.deleteByUserAndBook(userId, bookId);
    }
}