package com.unichristus.libraryapi.controller;

import com.unichristus.libraryapi.dto.request.BookCreateRequestDTO;
import com.unichristus.libraryapi.dto.request.BookUpdateRequestDTO;
import com.unichristus.libraryapi.dto.response.BookResponseDTO;
import com.unichristus.libraryapi.model.Book;
import com.unichristus.libraryapi.service.BookService;
import com.unichristus.libraryapi.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookResponseDTO> getAllBooks() {
        return bookService.findAll().stream().map((book) -> MapperUtil.parse(book, BookResponseDTO.class)).toList();
    }

    @GetMapping("{id}")
    public Book getBookById(@PathVariable UUID id) {
        return bookService.findBookByIdOrThrow(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //TODO: Adicionar CreatedResource para retornar o Location do recurso criado
    public BookResponseDTO createBook(@RequestBody @Valid BookCreateRequestDTO bookCreateRequestDTO) {
        Book createdBook = bookService.createBook(bookCreateRequestDTO);
        return MapperUtil.parse(createdBook, BookResponseDTO.class);
    }

    @PutMapping("{id}") //TODO: Put ou Patch?
    public void updateBook(@PathVariable UUID id, @RequestBody BookUpdateRequestDTO bookUpdateRequestDTO) {
        bookService.updateBook(id, bookUpdateRequestDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable UUID id) {
        bookService.deleteBookById(id);
    }
}
