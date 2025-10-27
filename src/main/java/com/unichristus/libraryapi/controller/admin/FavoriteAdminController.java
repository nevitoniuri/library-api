package com.unichristus.libraryapi.controller.admin;

import com.unichristus.libraryapi.dto.response.FavoriteResponse;
import com.unichristus.libraryapi.service.FavoriteService;
import com.unichristus.libraryapi.util.MapperUtil;
import com.unichristus.libraryapi.util.ServiceURIs;
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

    private final FavoriteService favoriteService;

    @GetMapping
    @Operation(summary = "Listar todos os favoritos (admin)")
    public Page<FavoriteResponse> getAll(Pageable pageable) {
        return favoriteService.findAll(pageable)
                .map(fav -> MapperUtil.parse(fav, FavoriteResponse.class));
    }
}
