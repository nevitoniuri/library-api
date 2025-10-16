package com.unichristus.libraryapi.service;

import com.unichristus.libraryapi.exception.ServiceError;
import com.unichristus.libraryapi.exception.ServiceException;
import com.unichristus.libraryapi.model.Book;
import com.unichristus.libraryapi.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book findById(UUID id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceError.NOT_FOUND));
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBookById(UUID id) {
        bookRepository.delete(findById(id));
    }
}
