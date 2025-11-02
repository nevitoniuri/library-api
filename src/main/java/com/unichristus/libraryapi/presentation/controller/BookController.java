package com.unichristus.libraryapi.presentation.controller;

import com.unichristus.libraryapi.application.dto.response.BookPdfResponse;
import com.unichristus.libraryapi.application.dto.response.BookResponse;
import com.unichristus.libraryapi.application.usecase.book.BookPdfUseCase;
import com.unichristus.libraryapi.application.usecase.book.BookUseCase;
import com.unichristus.libraryapi.presentation.common.ServiceURIs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceURIs.BOOKS_RESOURCE)
public class BookController {

    private final BookUseCase bookUseCase;
    private final BookPdfUseCase bookPdfUseCase;

    @GetMapping("{bookId}")
    public BookPdfResponse getBookById(@PathVariable UUID bookId) {
        return bookPdfUseCase.getBookWithPdf(bookId);
    }

    @GetMapping
    //TODO: Implementar filtros de busca (nome, autor, categoria, etc)
    public Page<BookResponse> getAllBooks(Pageable pageable) {
        return bookUseCase.getAllBooks(pageable);
    }

}
