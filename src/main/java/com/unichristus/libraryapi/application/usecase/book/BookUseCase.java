package com.unichristus.libraryapi.application.usecase.book;

import com.unichristus.libraryapi.application.annotation.UseCase;
import com.unichristus.libraryapi.application.dto.request.BookCreateRequest;
import com.unichristus.libraryapi.application.dto.request.BookUpdateRequest;
import com.unichristus.libraryapi.application.dto.response.BookResponse;
import com.unichristus.libraryapi.application.mapper.BookResponseMapper;
import com.unichristus.libraryapi.domain.book.Book;
import com.unichristus.libraryapi.domain.book.BookService;
import com.unichristus.libraryapi.domain.category.Category;
import com.unichristus.libraryapi.domain.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class BookUseCase {

    private final BookService bookService;
    private final CategoryService categoryService;

    public Page<BookResponse> getAllBooks(Pageable pageable) {
        Page<Book> books = bookService.findAll(pageable);
        return books.map(BookResponseMapper::toBookResponse);
    }

    public BookResponse createBook(BookCreateRequest request) {
        Set<Category> categories = new HashSet<>();
        if (request.categories() != null && !request.categories().isEmpty()) {
            categories = categoryService.findCategoriesByIds(request.categories());
        }

        Book createdBook = bookService.createBook(
                request.title(),
                request.isbn(),
                request.numberOfPages(),
                request.publicationDate(),
                request.coverUrl(),
                categories

        );
        return BookResponseMapper.toBookResponse(createdBook);
    }

    public void updateBook(UUID bookId, BookUpdateRequest request) {
        bookService.updateBook(
                bookId,
                request.title(),
                request.isbn(),
                request.numberOfPages(),
                request.publicationDate(),
                request.available()
        );
    }

    public void deleteBook(UUID bookId) {
        bookService.deleteBookById(bookId);
    }
}
