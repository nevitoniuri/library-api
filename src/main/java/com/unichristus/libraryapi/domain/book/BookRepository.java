package com.unichristus.libraryapi.domain.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository {

    Book save(Book book);

    Optional<Book> findById(UUID id);

    boolean existsBookByIsbn(String isbn);

    Page<Book> findBooksByHasPdfTrue(Pageable pageable);

    void delete(Book book);
}
