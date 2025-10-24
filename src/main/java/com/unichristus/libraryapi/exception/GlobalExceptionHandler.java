package com.unichristus.libraryapi.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException ex) {
        log.error("ServiceException: {}", ex.getMessage(), ex);
        String description = ex.getError().getDescription();
        if (ex.getParameters() != null) {
            for (Object parameter : ex.getParameters()) {
                description = description.replaceFirst("\\{}", parameter.toString());
            }
        }
        return ResponseEntity.status(ex.getError().getHttpStatus())
                .body(new ErrorResponse(ex.getError().getCode(), description));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        log.error("Validation Exception: {}", ex.getMessage());
        List<FieldError> fieldErrors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String fieldName = ((org.springframework.validation.FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    return new FieldError(fieldName, errorMessage);
                })
                .toList();
        var validationErrorResponse = new ValidationErrorResponse();
        validationErrorResponse.setCode("VALIDATION_ERROR");
        validationErrorResponse.setMessage("Validation failed");
        validationErrorResponse.setFieldErrors(fieldErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Generic Exception: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage()));
    }
}
