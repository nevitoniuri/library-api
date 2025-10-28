package com.unichristus.libraryapi.application.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse extends ErrorResponse {
    private List<FieldErrorResponse> fieldErrors;
}

