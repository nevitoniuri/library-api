package com.unichristus.libraryapi.presentation.controller;

import com.unichristus.libraryapi.application.dto.request.ReviewCreateRequest;
import com.unichristus.libraryapi.application.dto.request.ReviewUpdateRequest;
import com.unichristus.libraryapi.application.dto.response.BookAverageScoreResponse;
import com.unichristus.libraryapi.application.dto.response.ReviewResponse;
import com.unichristus.libraryapi.application.usecase.review.ReviewUseCase;
import com.unichristus.libraryapi.infrastructure.security.LoggedUser;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Reviews", description = "Operações com avaliações de livros")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = ServiceURIs.REVIEWS_RESOURCE)
public class ReviewController {

    private final ReviewUseCase reviewUseCase;

    @GetMapping
    @Operation(summary = "Listar avaliações recentes", description = "Lista todas as avaliações com paginação e filtros opcionais")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de avaliações retornada com sucesso")
    })
    public Page<ReviewResponse> getAllReviews(@LoggedUser UUID userId, Pageable pageable) {
        return reviewUseCase.getAllReviews(userId, pageable);
    }

    @GetMapping("/average")
    @Operation(summary = "Obter pontuações médias dos livros", description = "Retorna a média de avaliações de todos os livros ou de livros específicos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pontuações médias retornadas com sucesso")
    })
    public List<BookAverageScoreResponse> getAverageScores(
            @RequestParam(required = false) List<UUID> bookIds
    ) {
        return reviewUseCase.getAverageScores(bookIds);
    }

    @GetMapping("/{reviewId}")
    @Operation(summary = "Obter detalhes de uma avaliação específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliação encontrada"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    public ReviewResponse getReviewById(@PathVariable UUID reviewId) {
        return reviewUseCase.getReviewById(reviewId);
    }

    @PostMapping
    @Operation(summary = "Criar uma nova avaliação de livro", description = "Cria uma avaliação para um livro pelo usuário logado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Avaliação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
            @ApiResponse(responseCode = "409", description = "Usuário já avaliou este livro")
    })
    public ResponseEntity<ReviewResponse> createReview(
            @RequestBody @Valid ReviewCreateRequest request,
            @LoggedUser UUID userId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewUseCase.createReview(userId, request));
    }

    @PatchMapping("/{reviewId}")
    @Operation(summary = "Atualizar uma avaliação existente", description = "Atualiza uma avaliação do usuário logado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliação atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Usuário não tem permissão para atualizar esta avaliação"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    public ReviewResponse updateReview(
            @PathVariable UUID reviewId,
            @RequestBody @Valid ReviewUpdateRequest request,
            @LoggedUser UUID userId
    ) {
        return reviewUseCase.updateReview(reviewId, userId, request);
    }

    @DeleteMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remover uma avaliação de livro", description = "Remove uma avaliação do usuário logado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Avaliação removida com sucesso"),
            @ApiResponse(responseCode = "403", description = "Usuário não tem permissão para remover esta avaliação"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    public void deleteReview(
            @PathVariable UUID reviewId,
            @LoggedUser UUID userId
    ) {
        reviewUseCase.deleteReview(reviewId, userId);
    }
}