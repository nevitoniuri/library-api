package com.unichristus.libraryapi.infrastructure.persistence.book;

import com.unichristus.libraryapi.domain.book.Book;
import com.unichristus.libraryapi.domain.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BookJpaRepositoryAdapter implements BookRepository {

    private final BookJpaRepository bookJpaRepository;

    @Override
    public Book save(Book book) {
        return bookJpaRepository.save(book);
    }

    @Override
    public Optional<Book> findById(UUID id) {
        return bookJpaRepository.findById(id);
    }

    @Override
    public boolean existsBookByIsbn(String isbn) {
        return bookJpaRepository.existsBookByIsbn(isbn);
    }

    @Override
    public Page<Book> findBooksByHasPdfTrue(Pageable pageable) {
        return bookJpaRepository.findBooksByHasPdfTrue(pageable);
    }

    @Override
    public void delete(Book book) {
        bookJpaRepository.delete(book);
    }
}
