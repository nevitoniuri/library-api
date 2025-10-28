package com.unichristus.libraryapi.presentation.controller;

import com.unichristus.libraryapi.application.dto.request.StartReadingRequest;
import com.unichristus.libraryapi.application.dto.request.UpdateReadingProgressRequest;
import com.unichristus.libraryapi.application.dto.response.StartReadingResponse;
import com.unichristus.libraryapi.application.service.StartReadingService;
import com.unichristus.libraryapi.application.util.ServiceURIs;
import com.unichristus.libraryapi.domain.reading.ReadingService;
import com.unichristus.libraryapi.infra.security.LoggedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceURIs.READINGS_RESOURCE)
@Tag(name = "Readings", description = "Gerenciamento de leituras")
public class ReadingController {

    private final StartReadingService startReadingService;
    private final ReadingService readingService;

    @Operation(summary = "Iniciar nova leitura", description = "Inicia uma nova leitura de um livro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Leitura iniciada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "409", description = "Já existe uma leitura ativa deste livro")
    })
    @PostMapping
    public ResponseEntity<StartReadingResponse> startReading(
            @RequestBody @Valid StartReadingRequest request,
            @LoggedUser UUID userId
    ) {
        StartReadingResponse readingResponse = startReadingService.startReading(request.bookId(), userId);
        return ResponseEntity.created(URI.create(ServiceURIs.READINGS_RESOURCE + "/" + readingResponse.getId())).body(readingResponse);
    }

    @Operation(summary = "Atualizar leitura", description = "Atualiza uma leitura existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Leitura atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
            @ApiResponse(responseCode = "404", description = "Leitura não encontrada"),
    })
    @PatchMapping("{readingId}")
    public void updateReadingProgress(
            @PathVariable UUID readingId,
            @RequestBody @Valid UpdateReadingProgressRequest request,
            @LoggedUser UUID userId
    ) {
        readingService.updateReadingProgress(readingId, userId, request.currentPage());
    }
}
