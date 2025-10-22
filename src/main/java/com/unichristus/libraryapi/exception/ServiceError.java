package com.unichristus.libraryapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ServiceError {
    GENERIC_ERROR("GENERIC_ERROR", HttpStatus.BAD_REQUEST, "Ocorreu um erro, tente novamente mais tarde. Info: {}"),

    //USER ERRORS
    USER_NOT_FOUND("USER_NOT_FOUND", HttpStatus.NOT_FOUND, "Usuário não encontrado: {}"),
    USER_NOT_AUTHENTICATED("USER_NOT_AUTHENTICATED", HttpStatus.UNAUTHORIZED, "Usuário não autenticado."),
    EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS", HttpStatus.CONFLICT, "Já existe um usuário cadastrado com o email: {}"),

    //BOOK ERRORS
    BOOK_NOT_FOUND("BOOK_NOT_FOUND", HttpStatus.NOT_FOUND, "Livro não encontrado: {}"),
    ISBN_ALREADY_EXISTS("ISBN_ALREADY_EXISTS", HttpStatus.CONFLICT, "Já existe um livro cadastrado com o ISBN: {}"),

    //READING ERRORS
    READING_NOT_FOUND("READING_NOT_FOUND", HttpStatus.NOT_FOUND, "Leitura não encontrada: {}"),
    READING_IN_PROGRESS("READING_IN_PROGRESS", HttpStatus.CONFLICT, "Já existe uma leitura em progresso para o usuário: {} e o livro: {}"),
    READING_INVALID_PAGE_PROGRESS("READING_INVALID_PAGE_PROGRESS", HttpStatus.BAD_REQUEST, "O progresso da leitura não pode ser menor que a página atual: {}"),
    READING_FINISHED("READING_FINISHED", HttpStatus.BAD_REQUEST, "A leitura já foi finalizada: {}"),
    READING_USER_MISMATCH("READING_USER_MISMATCH", HttpStatus.BAD_REQUEST, "O usuário {} não corresponde ao usuário da leitura: {}"),

    //FAVORITE ERRORS
    FAVORITE_ALREADY_EXISTS("FAVORITE_ALREADY_EXISTS", HttpStatus.CONFLICT, "Já existe um favorito cadastrado para o usuário: {} e o livro: {}"),
    FAVORITE_NOT_FOUND("FAVORITE_NOT_FOUND", HttpStatus.NOT_FOUND, "Favorito não encontrado: {}"),
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
