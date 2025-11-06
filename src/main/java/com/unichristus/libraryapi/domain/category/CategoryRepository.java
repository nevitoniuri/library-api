package com.unichristus.libraryapi.domain.category;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface CategoryRepository {

    Category save(Category category);

    Optional<Category> findById(UUID categoryId);

    List<Category> findByActiveTrue();

    List<Category> findAll();

    Optional<Category> findByNameIgnoreCase(String name);

    Set<Category> findAllByIdIn(List<UUID> ids);
}
