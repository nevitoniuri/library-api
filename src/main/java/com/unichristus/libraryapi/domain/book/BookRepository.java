package com.unichristus.libraryapi.domain.book;

import com.unichristus.libraryapi.domain.common.PageRequestDomain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository {

    Book save(Book book);

    Optional<Book> findById(UUID id);

    boolean existsBookByIsbn(String isbn);

    List<Book> findBooksByHasPdfTrue(PageRequestDomain pageRequest);

    void delete(Book book);
}
