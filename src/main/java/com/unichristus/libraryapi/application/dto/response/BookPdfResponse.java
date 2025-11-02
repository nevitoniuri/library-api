package com.unichristus.libraryapi.application.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class BookPdfResponse extends BookResponse {
    private String pdfUrl;
}
