package com.unichristus.libraryapi.presentation.controller.admin;

import com.unichristus.libraryapi.application.dto.request.BookCreateRequest;
import com.unichristus.libraryapi.application.dto.request.BookUpdateRequest;
import com.unichristus.libraryapi.application.dto.response.BookResponse;
import com.unichristus.libraryapi.application.usecase.book.BookPdfUseCase;
import com.unichristus.libraryapi.application.usecase.book.BookUseCase;
import com.unichristus.libraryapi.presentation.common.ServiceURIs;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceURIs.BOOKS_ADMIN)
@Tag(name = "Admin - Books")
public class BookAdminController {

    private final BookPdfUseCase bookPdfUseCase;
    private final BookUseCase bookUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //TODO: Adicionar CreatedResource para retornar o Location do recurso criado
    public BookResponse createBook(@RequestBody @Valid BookCreateRequest request) {
        return bookUseCase.createBook(request);
    }

    //TODO: receber entidade completa e atualizar todos os campos?
    @PatchMapping("{bookId}")
    public void updateBook(@PathVariable UUID bookId, @RequestBody BookUpdateRequest request) {
        bookUseCase.updateBook(bookId, request);
    }

    @PostMapping(path = "/{bookId}/upload", consumes = {"multipart/form-data"})
    public void uploadBook(@PathVariable UUID bookId, @RequestParam("file") MultipartFile file) {
        bookPdfUseCase.uploadBookPdf(bookId, file);
    }

    @DeleteMapping("{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable UUID bookId) {
        bookUseCase.deleteBook(bookId);
    }
}