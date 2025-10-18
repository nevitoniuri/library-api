package com.unichristus.libraryapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ServiceError {
    GENERIC_ERROR("GENERIC_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro, tente novamente mais tarde."),
    BOOK_NOT_FOUND("BOOK_NOT_FOUND", HttpStatus.NOT_FOUND, "Livro não encontrado: {}"),
    USER_NOT_FOUND("USER_NOT_FOUND", HttpStatus.NOT_FOUND, "Usuário não encontrado: {}"),
    READING_NOT_FOUND("READING_NOT_FOUND", HttpStatus.NOT_FOUND, "Leitura não encontrada: {}"),
    ISBN_ALREADY_EXISTS("ISBN_ALREADY_EXISTS", HttpStatus.BAD_REQUEST, "Já existe um livro cadastrado com o ISBN: {}"),
    READING_ALREADY_EXISTS("READING_ALREADY_EXISTS", HttpStatus.BAD_REQUEST, "Já existe uma leitura cadastrada para o usuário: {} e o livro: {}"),
    READING_INVALID_PAGE_PROGRESS("READING_INVALID_PAGE_PROGRESS", HttpStatus.BAD_REQUEST, "O progresso da leitura não pode ser menor que a página atual: {}")
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
