package com.unichristus.libraryapi.service;

import com.unichristus.libraryapi.dto.request.BookCreateRequest;
import com.unichristus.libraryapi.dto.request.BookUpdateRequest;
import com.unichristus.libraryapi.exception.ServiceError;
import com.unichristus.libraryapi.exception.ServiceException;
import com.unichristus.libraryapi.model.Book;
import com.unichristus.libraryapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final FileStorageService fileStorageService;

    @Value("${minio.bucket.max-size-mb}")
    private int maxFileSizeMb;

    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findBooksByHasPdfTrue(pageable);
    }

    public Book findBookByIdOrThrow(UUID id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceError.BOOK_NOT_FOUND, id));
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public String getBookPdfUrl(Book book) {
        return fileStorageService.generatePresignedUrl(book.getId().toString());
    }

    public void uploadBookPdf(UUID bookId, MultipartFile file) {
        Book book = findBookByIdOrThrow(bookId);
        if (book.isHasPdf()) {
            throw new ServiceException(ServiceError.BOOK_PDF_ALREADY_EXISTS, bookId);
        }
        if (file.getSize() > maxFileSizeMb) {
            throw new ServiceException(ServiceError.BOOK_PDF_SIZE_EXCEEDED, maxFileSizeMb);
        }
        fileStorageService.uploadPdf(file, book.getId().toString());
        book.setHasPdf(true);
        save(book);
    }

    public Book createBook(BookCreateRequest dto) {
        validateISBNUnique(dto.isbn());
        Book book = Book.builder()
                .title(dto.title())
                .isbn(dto.isbn())
                .numberOfPages(dto.numberOfPages())
                .publicationDate(dto.publicationDate())
                .build();
        return save(book);
    }

    public void updateBook(UUID id, BookUpdateRequest dto) {
        Book book = findBookByIdOrThrow(id);
        boolean changed = false;
        if (dto.title() != null && !dto.title().equals(book.getTitle())) {
            book.setTitle(dto.title());
            changed = true;
        }
        if (dto.isbn() != null && !dto.isbn().equals(book.getIsbn())) {
            validateISBNUnique(dto.isbn());
            book.setIsbn(dto.isbn());
            changed = true;
        }
        if (dto.numberOfPages() != null && !dto.numberOfPages().equals(book.getNumberOfPages())) {
            book.setNumberOfPages(dto.numberOfPages());
            changed = true;
        }
        if (dto.publicationDate() != null && !dto.publicationDate().equals(book.getPublicationDate())) {
            book.setPublicationDate(dto.publicationDate());
            changed = true;
        }
        if (changed) {
            save(book);
        }
    }

    private void validateISBNUnique(String isbn) {
        if (bookRepository.existsBookByIsbn(isbn)) {
            throw new ServiceException(ServiceError.ISBN_ALREADY_EXISTS, isbn);
        }
    }

    public void deleteBookById(UUID id) {
        bookRepository.delete(findBookByIdOrThrow(id));
    }
}
