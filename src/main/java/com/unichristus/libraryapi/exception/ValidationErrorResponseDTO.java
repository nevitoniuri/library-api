package com.unichristus.libraryapi.exception;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponseDTO extends ErrorResponseDTO {
    private List<FieldErrorDTO> fieldErrors;
}

