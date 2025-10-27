package com.unichristus.libraryapi.repository;

import com.unichristus.libraryapi.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    boolean existsBookByIsbn(String isbn);

    Page<Book> findBooksByHasPdfTrue(Pageable pageable);
}
