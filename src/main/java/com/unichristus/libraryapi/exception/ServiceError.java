package com.unichristus.libraryapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ServiceError {
    GENERIC_ERROR("GENERIC_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro, tente novamente mais tarde."),
    BOOK_NOT_FOUND("BOOK_NOT_FOUND", HttpStatus.NOT_FOUND, "Livro não encontrado: {}"),
    ISBN_ALREADY_EXISTS("ISBN_ALREADY_EXISTS", HttpStatus.BAD_REQUEST, "Já existe um livro cadastrado com o ISBN: {}")
    ;

    private final String code;
    private final HttpStatus httpStatus;
    private final String description;

    ServiceError(String code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.description = description;
    }
}
