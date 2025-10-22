package com.unichristus.libraryapi.controller;

import com.unichristus.libraryapi.annotation.LoggedUser;
import com.unichristus.libraryapi.dto.request.StartReadingRequest;
import com.unichristus.libraryapi.dto.response.ReadingResponseDTO;
import com.unichristus.libraryapi.model.Reading;
import com.unichristus.libraryapi.security.CustomUserDetails;
import com.unichristus.libraryapi.service.ReadingService;
import com.unichristus.libraryapi.util.MapperUtil;
import com.unichristus.libraryapi.util.ServiceURIs;
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
@RequestMapping(ServiceURIs.READINGS)
@Tag(name = "Readings", description = "Endpoints para gerenciamento de leituras")
public class ReadingController {

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
    public ResponseEntity<ReadingResponseDTO> startReading(
            @RequestBody @Valid StartReadingRequest request,
            @LoggedUser CustomUserDetails userDetails
    ) {
        Reading reading = readingService.startReading(request.getBookId(), userDetails.toEntityReference());
        ReadingResponseDTO response = MapperUtil.parse(reading, ReadingResponseDTO.class);
        return ResponseEntity.created(URI.create(ServiceURIs.READINGS + "/" + reading.getId())).body(response);
    }

    @PatchMapping("/{id}/update-progress")
    public void updateReadingProgress(
            @PathVariable(value = "id") UUID readingId,
            @RequestParam UUID userId,
            @RequestParam int currentPage
    ) {
        readingService.updateReadingProgress(readingId, userId, currentPage);
    }

    @PatchMapping("/{id}/finish")
    public void finishReading(
            @PathVariable(value = "id") UUID readingId,
            @RequestParam UUID userId
    ) {
        readingService.finishReading(readingId);
    }
}
