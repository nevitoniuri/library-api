package com.unichristus.libraryapi.presentation.controller;

import com.unichristus.libraryapi.application.dto.request.CategoryRequest;
import com.unichristus.libraryapi.application.dto.response.BookResponse;
import com.unichristus.libraryapi.application.dto.response.CategoryResponse;
import com.unichristus.libraryapi.application.usecase.category.CategoryUseCase;
import com.unichristus.libraryapi.presentation.common.ServiceURIs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Categories", description = "Operações com categorias de livros")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = ServiceURIs.CATEGORIES_RESOURCE)
public class CategoryController {

    private final CategoryUseCase categoryUseCase;

    @GetMapping
    @Operation(summary = "Listar todas as categorias ativas", description = "Lista todas as categorias que estão ativas no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso")
    })
    public List<CategoryResponse> getAllActiveCategories() {
        return categoryUseCase.getAllActiveCategories();
    }

    @GetMapping("/{categoryId}")
    @Operation(summary = "Obter detalhes de uma categoria", description = "Retorna os detalhes de uma categoria específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public CategoryResponse getCategoryById(@PathVariable UUID categoryId) {
        return categoryUseCase.getCategoryById(categoryId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar uma nova categoria", description = "Cria uma nova categoria no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Já existe uma categoria com este nome")
    })
    public CategoryResponse createCategory(@RequestBody @Valid CategoryRequest request) {
        return categoryUseCase.createCategory(request);
    }

    @PutMapping("/{categoryId}")
    @Operation(summary = "Atualizar uma categoria", description = "Atualiza os dados de uma categoria existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "409", description = "Já existe uma categoria com este nome")
    })
    public CategoryResponse updateCategory(
            @PathVariable UUID categoryId,
            @RequestBody @Valid CategoryRequest request
    ) {
        return categoryUseCase.updateCategory(categoryId, request);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Desativar uma categoria", description = "Desativa uma categoria (soft delete)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria desativada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public void deleteCategory(@PathVariable UUID categoryId) {
        categoryUseCase.deleteCategory(categoryId);
    }

    @GetMapping("/{categoryId}/books")
    @Operation(summary = "Listar livros de uma categoria", description = "Retorna todos os livros associados a uma categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public Page<BookResponse> getBooksByCategory(
            @PathVariable UUID categoryId,
            Pageable pageable
    ) {
        return categoryUseCase.getBooksByCategory(categoryId, pageable);
    }
}