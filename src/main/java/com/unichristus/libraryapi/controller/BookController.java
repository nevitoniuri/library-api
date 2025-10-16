package com.unichristus.libraryapi.controller;

import com.unichristus.libraryapi.dto.request.BookCreateRequestDTO;
import com.unichristus.libraryapi.dto.response.BookResponseDTO;
import com.unichristus.libraryapi.model.Book;
import com.unichristus.libraryapi.service.BookService;
import com.unichristus.libraryapi.util.MapperUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("{id}")
    public Book getBookById(@PathVariable UUID id) {
        return bookService.findById(id);
    }

    @GetMapping
    public List<BookResponseDTO> getAllBooks() {
        return bookService.findAll().stream().map((book) -> MapperUtil.parse(book, BookResponseDTO.class)).toList();
    }

    @PostMapping
    public Book createBook(@RequestBody @Valid BookCreateRequestDTO bookCreateRequestDTO) {
        return bookService.createBook(MapperUtil.parse(bookCreateRequestDTO, Book.class));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable UUID id) {
        bookService.deleteBookById(id);
    }
}
