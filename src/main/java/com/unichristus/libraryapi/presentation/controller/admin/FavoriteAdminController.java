package com.unichristus.libraryapi.presentation.controller.admin;

import com.unichristus.libraryapi.application.common.ServiceURIs;
import com.unichristus.libraryapi.application.dto.response.FavoriteResponse;
import com.unichristus.libraryapi.application.mapper.FavoriteResponseMapper;
import com.unichristus.libraryapi.domain.common.PageRequestDomain;
import com.unichristus.libraryapi.domain.favorite.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceURIs.FAVORITES_ADMIN)
public class FavoriteAdminController {

    private final FavoriteService favoriteService;

    @GetMapping
    @Operation(summary = "Listar todos os favoritos (admin)")
    public Page<FavoriteResponse> getAll(Pageable pageable) {
        var pageRequest = new PageRequestDomain(pageable.getPageNumber(), pageable.getPageSize());
        List<FavoriteResponse> favorites = favoriteService.findAll(pageRequest)
                .stream()
                .map(FavoriteResponseMapper::toFavoriteResponse)
                .toList();
        return new PageImpl<>(favorites, pageable, favorites.size());
    }
}
