package com.unichristus.libraryapi.domain.reading.exception;

import com.unichristus.libraryapi.domain.exception.DomainError;
import com.unichristus.libraryapi.domain.exception.DomainException;

public class InvalidPageProgressException extends DomainException {
    public InvalidPageProgressException() {
        super(DomainError.READING_INVALID_PAGE_PROGRESS);
    }
}
