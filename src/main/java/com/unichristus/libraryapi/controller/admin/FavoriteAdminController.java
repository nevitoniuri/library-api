package com.unichristus.libraryapi.controller.admin;

import com.unichristus.libraryapi.dto.response.FavoriteResponse;
import com.unichristus.libraryapi.model.User;
import com.unichristus.libraryapi.service.FavoriteService;
import com.unichristus.libraryapi.service.UserService;
import com.unichristus.libraryapi.util.MapperUtil;
import com.unichristus.libraryapi.util.ServiceURIs;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceURIs.FAVORITES_ADMIN)
public class FavoriteAdminController {

    private final FavoriteService favoriteService;
    private final UserService userService;

    @GetMapping
    @Operation(summary = "Listar todos os favoritos (admin)")
    public Page<FavoriteResponse> getAll(Pageable pageable) {
        return favoriteService.findAll(pageable)
                .map(fav -> MapperUtil.parse(fav, FavoriteResponse.class));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar favorito por userId e bookId (admin)")
    public void create(@RequestParam UUID userId, @RequestParam UUID bookId) {
        User user = userService.findUserByIdOrThrow(userId);
        favoriteService.favoriteBook(bookId, user);
    }

    @DeleteMapping("/{favoriteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir favorito por ID (admin)")
    public void delete(@PathVariable UUID favoriteId) {
        favoriteService.deleteFavorite(favoriteId);
    }
}
