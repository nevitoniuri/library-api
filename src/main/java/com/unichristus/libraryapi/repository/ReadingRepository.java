package com.unichristus.libraryapi.repository;

import com.unichristus.libraryapi.model.Book;
import com.unichristus.libraryapi.model.Reading;
import com.unichristus.libraryapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadingRepository extends JpaRepository<Reading, UUID> {

    List<Reading> findReadingsByUserOrderByLastReadedAtDesc(User user);
    Optional<Reading> findByUserAndBook(User user, Book book);
}
