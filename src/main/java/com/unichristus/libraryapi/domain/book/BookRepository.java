package com.unichristus.libraryapi.domain.book;

import com.unichristus.libraryapi.domain.common.PageRequestDomain;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository {

    Book save(Book book);

    Optional<Book> findById(UUID id);

    boolean existsBookByIsbn(String isbn);

    Page<Book> findBooksByHasPdfTrue(PageRequestDomain pageRequest);

    void delete(Book book);
}
