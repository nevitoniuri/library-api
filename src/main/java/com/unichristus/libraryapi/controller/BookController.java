package com.unichristus.libraryapi.controller;

import com.unichristus.libraryapi.dto.response.BookResponse;
import com.unichristus.libraryapi.service.BookService;
import com.unichristus.libraryapi.util.MapperUtil;
import com.unichristus.libraryapi.util.ServiceURIs;
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

    private final BookService bookService;

    @GetMapping
    //TODO: Implementar filtros de busca (nome, autor, categoria, etc)
    public Page<BookResponse> getAllBooks(Pageable pageable) {
        return bookService.findAll(pageable)
                .map((book) -> MapperUtil.parse(book, BookResponse.class));
    }

    @GetMapping("{id}")
    public BookResponse getBookById(@PathVariable UUID id) {
        return MapperUtil.parse(bookService.findBookByIdOrThrow(id), BookResponse.class);
    }
}
