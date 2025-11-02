package com.unichristus.libraryapi.application.mapper;

import com.unichristus.libraryapi.application.dto.response.BookPdfResponse;
import com.unichristus.libraryapi.application.dto.response.BookResponse;
import com.unichristus.libraryapi.domain.book.Book;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BookResponseMapper {

    public static BookResponse toBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .numberOfPages(book.getNumberOfPages())
                .publicationDate(book.getPublicationDate())
                .build();
    }

    public static BookPdfResponse toBookPdfResponse(Book book, String pdfUrl) {
        return BookPdfResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .numberOfPages(book.getNumberOfPages())
                .publicationDate(book.getPublicationDate())
                .pdfUrl(pdfUrl)
                .build();
    }

}
