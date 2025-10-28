package com.unichristus.libraryapi.domain.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    boolean existsBookByIsbn(String isbn);

    Page<Book> findBooksByHasPdfTrue(Pageable pageable);
}
