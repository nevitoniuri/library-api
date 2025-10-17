package com.unichristus.libraryapi.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceException extends RuntimeException {
    private final ServiceError error;
    private final Object[] parameters;

    public ServiceException(ServiceError error, Object... parameters) {
        super(error.getCode());
        this.error = error;
        this.parameters = parameters;
    }
}

