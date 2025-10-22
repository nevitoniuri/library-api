package com.unichristus.libraryapi.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class ServiceURIs {

    //API Paths
    public static final String BASE_API = "/api/v1";

    //Resource Paths
    public static final String AUTH = BASE_API + "/auth";
    public static final String BOOKS = BASE_API + "/books";
    public static final String USERS = BASE_API + "/users";
    public static final String READINGS = BASE_API + "/readings";
    public static final String FAVORITES = BASE_API + "/favorites";
}
