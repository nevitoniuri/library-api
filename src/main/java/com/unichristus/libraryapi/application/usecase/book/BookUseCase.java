package com.unichristus.libraryapi.application.usecase.book;

import com.unichristus.libraryapi.application.annotation.UseCase;
import com.unichristus.libraryapi.application.dto.request.BookCreateRequest;
import com.unichristus.libraryapi.application.dto.request.BookUpdateRequest;
import com.unichristus.libraryapi.application.dto.response.BookResponse;
import com.unichristus.libraryapi.application.mapper.BookResponseMapper;
import com.unichristus.libraryapi.domain.book.Book;
import com.unichristus.libraryapi.domain.book.BookService;
import com.unichristus.libraryapi.domain.common.PageRequestDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class BookUseCase {

    private final BookService bookService;

    public Page<BookResponse> getAllBooks(Pageable pageable) {
        var pageRequest = new PageRequestDomain(
                pageable.getPageNumber(),
                pageable.getPageSize()
        );
        Page<Book> books = bookService.findAll(pageRequest);
        return books.map(BookResponseMapper::toBookResponse);
    }

    public BookResponse createBook(BookCreateRequest request) {
        Book createdBook = bookService.createBook(
                request.title(),
                request.isbn(),
                request.numberOfPages(),
                request.publicationDate(),
                request.coverUrl()
        );
        return BookResponseMapper.toBookResponse(createdBook);
    }

    public void updateBook(UUID bookId, BookUpdateRequest request) {
        bookService.updateBook(
                bookId,
                request.title(),
                request.isbn(),
                request.numberOfPages(),
                request.publicationDate()
        );
    }

    public void deleteBook(UUID bookId) {
        bookService.deleteBookById(bookId);
    }
}
