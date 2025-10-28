package com.unichristus.libraryapi.presentation.controller.admin;

import com.unichristus.libraryapi.application.dto.request.BookCreateRequest;
import com.unichristus.libraryapi.application.dto.request.BookUpdateRequest;
import com.unichristus.libraryapi.application.dto.response.BookResponse;
import com.unichristus.libraryapi.domain.book.Book;
import com.unichristus.libraryapi.domain.book.BookService;
import com.unichristus.libraryapi.application.util.MapperUtil;
import com.unichristus.libraryapi.application.util.ServiceURIs;
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

    private final BookService bookService;

    @PostMapping(path = "/{bookId}/upload", consumes = {"multipart/form-data"})
    public void uploadBook(@PathVariable UUID bookId, @RequestParam("file") MultipartFile file) {
        bookService.uploadBookPdf(bookId, file);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //TODO: Adicionar CreatedResource para retornar o Location do recurso criado
    public BookResponse createBook(@RequestBody @Valid BookCreateRequest request) {
        Book createdBook = bookService.createBook(request.title(), request.isbn(),
                request.numberOfPages(), request.publicationDate(), request.coverUrl());
        return MapperUtil.parse(createdBook, BookResponse.class);
    }

    //TODO: receber entidade completa e atualizar todos os campos?
    @PatchMapping("{id}")
    public void updateBook(@PathVariable UUID id, @RequestBody BookUpdateRequest request) {
        bookService.updateBook(id, request.title(), request.isbn(), request.numberOfPages(), request.publicationDate());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable UUID id) {
        bookService.deleteBookById(id);
    }
}