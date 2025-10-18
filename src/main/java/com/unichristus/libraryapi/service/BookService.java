package com.unichristus.libraryapi.service;

import com.unichristus.libraryapi.dto.request.BookCreateRequestDTO;
import com.unichristus.libraryapi.dto.request.BookUpdateRequestDTO;
import com.unichristus.libraryapi.exception.ServiceError;
import com.unichristus.libraryapi.exception.ServiceException;
import com.unichristus.libraryapi.model.Book;
import com.unichristus.libraryapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findBookByIdOrThrow(UUID id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceError.BOOK_NOT_FOUND, id));
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book createBook(BookCreateRequestDTO dto) {
        validateISBNUnique(dto.isbn());
        Book book = Book.builder()
                .title(dto.title())
                .isbn(dto.isbn())
                .numberOfPages(dto.numberOfPages())
                .publicationDate(dto.publicationDate())
                .build();
        return save(book);
    }

    public void updateBook(UUID id, BookUpdateRequestDTO dto) {
        Book book = findBookByIdOrThrow(id);
        boolean changed = false;
        if (dto.title() != null && !dto.title().equals(book.getTitle())) {
            book.setTitle(dto.title());
            changed = true;
        }
        if (dto.isbn() != null && !dto.isbn().equals(book.getIsbn())) {
            validateISBNUnique(dto.isbn());
            book.setIsbn(dto.isbn());
            changed = true;
        }
        if (dto.numberOfPages() != null && !dto.numberOfPages().equals(book.getNumberOfPages())) {
            book.setNumberOfPages(dto.numberOfPages());
            changed = true;
        }
        if (dto.publicationDate() != null && !dto.publicationDate().equals(book.getPublicationDate())) {
            book.setPublicationDate(dto.publicationDate());
            changed = true;
        }
        if (changed) {
            save(book);
        }
    }

    private void validateISBNUnique(String isbn) {
        if (bookRepository.existsBookByIsbn(isbn)) {
            throw new ServiceException(ServiceError.ISBN_ALREADY_EXISTS, isbn);
        }
    }

    public void deleteBookById(UUID id) {
        bookRepository.delete(findBookByIdOrThrow(id));
    }
}
