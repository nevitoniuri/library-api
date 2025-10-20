package com.unichristus.libraryapi.repository;

import com.unichristus.libraryapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    // Método para verificar se um email já existe no banco de dados
    boolean existsByEmail(String email);
}