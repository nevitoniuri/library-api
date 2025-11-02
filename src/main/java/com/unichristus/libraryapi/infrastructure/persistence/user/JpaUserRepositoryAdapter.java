package com.unichristus.libraryapi.infrastructure.persistence.user;

import com.unichristus.libraryapi.domain.common.PageRequestDomain;
import com.unichristus.libraryapi.domain.user.User;
import com.unichristus.libraryapi.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaUserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email);
    }

    @Override
    public void delete(User user) {
        jpaUserRepository.delete(user);
    }

    @Override
    public Page<User> findAll(PageRequestDomain pageRequest) {
        return jpaUserRepository.findAll(PageRequest.of(pageRequest.page(), pageRequest.size()));
    }

    @Override
    public User save(User user) {
        return jpaUserRepository.save(user);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaUserRepository.findById(id);
    }
}
