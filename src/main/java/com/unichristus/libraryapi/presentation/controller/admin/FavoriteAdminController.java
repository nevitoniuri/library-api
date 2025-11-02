package com.unichristus.libraryapi.presentation.controller.admin;

import com.unichristus.libraryapi.application.dto.response.FavoriteResponse;
import com.unichristus.libraryapi.application.usecase.favorite.FavoriteBookUseCase;
import com.unichristus.libraryapi.presentation.common.ServiceURIs;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceURIs.FAVORITES_ADMIN)
public class FavoriteAdminController {

    private final FavoriteBookUseCase favoriteBookUseCase;

    @GetMapping
    @Operation(summary = "Listar todos os favoritos (admin)")
    public Page<FavoriteResponse> getAll(Pageable pageable) {
        return favoriteBookUseCase.getAll(pageable);
    }
}
