package com.unichristus.libraryapi.exception;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse extends ErrorResponse {
    private List<FieldError> fieldErrors;
}

