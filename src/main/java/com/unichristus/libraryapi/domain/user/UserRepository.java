package com.unichristus.libraryapi.domain.user;

import com.unichristus.libraryapi.domain.common.PageRequestDomain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(UUID id);

    List<User> findAll(PageRequestDomain pageRequest);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    void delete(User user);
}