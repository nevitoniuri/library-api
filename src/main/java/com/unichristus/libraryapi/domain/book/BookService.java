package com.unichristus.libraryapi.domain.book;

import com.unichristus.libraryapi.domain.book.exception.BookIsbnConflict;
import com.unichristus.libraryapi.domain.book.exception.BookNotFoundException;
import com.unichristus.libraryapi.domain.common.PageRequestDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> findAll(PageRequestDomain pageRequest) {
        return bookRepository.findBooksByHasPdfTrue(pageRequest);
    }

    public Book findBookByIdOrThrow(UUID id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book createBook(String title, String isbn, Integer numberOfPages, LocalDate publicationDate, String coverUrl) {
        validateISBNUnique(isbn);
        Book book = Book.builder()
                .title(title)
                .coverUrl(coverUrl)
                .isbn(isbn)
                .numberOfPages(numberOfPages)
                .publicationDate(publicationDate)
                .build();
        return save(book);
    }

    public void updateBook(UUID id, String title, String isbn, Integer numberOfPages, LocalDate publicationDate) {
        Book book = findBookByIdOrThrow(id);
        boolean changed = false;
        if (title != null && !title.equals(book.getTitle())) {
            book.setTitle(title);
            changed = true;
        }
        if (isbn != null && !isbn.equals(book.getIsbn())) {
            validateISBNUnique(isbn);
            book.setIsbn(isbn);
            changed = true;
        }
        if (numberOfPages != null && !numberOfPages.equals(book.getNumberOfPages())) {
            book.setNumberOfPages(numberOfPages);
            changed = true;
        }
        if (publicationDate != null && !publicationDate.equals(book.getPublicationDate())) {
            book.setPublicationDate(publicationDate);
            changed = true;
        }
        if (changed) {
            save(book);
        }
    }

    private void validateISBNUnique(String isbn) {
        if (bookRepository.existsBookByIsbn(isbn)) {
            throw new BookIsbnConflict(isbn);
        }
    }

    public void deleteBookById(UUID id) {
        bookRepository.delete(findBookByIdOrThrow(id));
    }
}
