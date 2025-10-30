package com.unichristus.libraryapi.presentation.controller;

import com.unichristus.libraryapi.application.common.MapperUtil;
import com.unichristus.libraryapi.application.common.ServiceURIs;
import com.unichristus.libraryapi.application.dto.response.BookResponse;
import com.unichristus.libraryapi.domain.book.BookService;
import com.unichristus.libraryapi.domain.common.PageRequestDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceURIs.BOOKS_RESOURCE)
public class BookController {

    private final BookService bookService;

    @GetMapping
    //TODO: Implementar filtros de busca (nome, autor, categoria, etc)
    public Page<BookResponse> getAllBooks(Pageable pageable) {
        var pageRequest = new PageRequestDomain(pageable.getPageNumber(), pageable.getPageSize());
        List<BookResponse> books = bookService.findAll(pageRequest)
                .stream()
                .map(book -> MapperUtil.parse(book, BookResponse.class))
                .toList();
        return new PageImpl<>(books, pageable, books.size());
    }

    @GetMapping("{id}")
    public BookResponse getBookById(@PathVariable UUID id) {
        return MapperUtil.parse(bookService.findBookByIdOrThrow(id), BookResponse.class);
    }
}
