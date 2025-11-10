package com.unichristus.libraryapi.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    List<Category> findByActiveTrue();

    Optional<Category> findByNameIgnoreCase(String name);

    Set<Category> findAllByIdIn(List<UUID> ids);

}
