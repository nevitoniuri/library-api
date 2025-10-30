package com.unichristus.libraryapi.domain.common;

public record PageRequestDomain(int page, int size) {
    public PageRequestDomain {
        if (page < 0) {
            throw new IllegalArgumentException("Page number cannot be negative");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Page size must be greater than zero");
        }
    }
}
