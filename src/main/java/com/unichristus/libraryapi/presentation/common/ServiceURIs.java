package com.unichristus.libraryapi.presentation.common;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class ServiceURIs {

    //API Paths
    public static final String V1 = "/v1";
    public static final String API = "/api";
    public static final String BASE_API = API + V1;

    //Resource Paths
    public static final String HOME_RESOURCE = BASE_API + "/home";
    public static final String AUTH_RESOURCE = BASE_API + "/auth";
    private static final String BOOKS = "/books";
    public static final String BOOKS_RESOURCE = BASE_API + BOOKS;
    private static final String USERS = "/users";
    public static final String USERS_RESOURCE = BASE_API + USERS;
    public static final String READINGS_RESOURCE = BASE_API + "/readings";
    public static final String FAVORITES_RESOURCE = BASE_API + USERS + "/me/favorites";

    //Admin Paths
    private static final String ADMIN = API + "/admin";
    public static final String ADMIN_USERS = ADMIN + USERS;
    public static final String BOOKS_ADMIN = ADMIN + BOOKS;
    public static final String FAVORITES_ADMIN = ADMIN + "/favorites";
}
