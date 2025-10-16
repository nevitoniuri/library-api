package com.unichristus.libraryapi.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceException extends RuntimeException {
    private final ServiceError error;

    public ServiceException(ServiceError error) {
        super(error.getCode());
        this.error = error;
    }
}

