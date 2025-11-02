package com.unichristus.libraryapi.infrastructure.persistence.book;

import com.unichristus.libraryapi.domain.book.Book;
import com.unichristus.libraryapi.domain.book.BookRepository;
import com.unichristus.libraryapi.domain.common.PageRequestDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaBookRepositoryAdapter implements BookRepository {

    private final JpaBookRepository jpaBookRepository;

    @Override
    public Book save(Book book) {
        return jpaBookRepository.save(book);
    }

    @Override
    public Optional<Book> findById(UUID id) {
        return jpaBookRepository.findById(id);
    }

    @Override
    public boolean existsBookByIsbn(String isbn) {
        return jpaBookRepository.existsBookByIsbn(isbn);
    }

    @Override
    public Page<Book> findBooksByHasPdfTrue(PageRequestDomain pageRequest) {
        return jpaBookRepository.findBooksByHasPdfTrue(PageRequest.of(pageRequest.page(), pageRequest.size()));
    }

    @Override
    public void delete(Book book) {
        jpaBookRepository.delete(book);
    }
}
