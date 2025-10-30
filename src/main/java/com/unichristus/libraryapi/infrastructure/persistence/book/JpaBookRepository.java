package com.unichristus.libraryapi.infrastructure.persistence.book;

import com.unichristus.libraryapi.domain.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaBookRepository extends JpaRepository<Book, UUID> {

    boolean existsBookByIsbn(String isbn);

    Page<Book> findBooksByHasPdfTrue(Pageable pageable);
}