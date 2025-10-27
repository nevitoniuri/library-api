package com.unichristus.libraryapi.service;

import com.unichristus.libraryapi.dto.request.UserUpdateRequest;
import com.unichristus.libraryapi.exception.ServiceError;
import com.unichristus.libraryapi.exception.ServiceException;
import com.unichristus.libraryapi.model.User;
import com.unichristus.libraryapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User findUserByIdOrThrow(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceError.USER_NOT_FOUND, id));
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void updateUser(UUID id, UserUpdateRequest dto) {
        User user = findUserByIdOrThrow(id);
        boolean changed = false;

        if (dto.name() != null && !dto.name().equals(user.getName())) {
            user.setName(dto.name());
            changed = true;
        }

        if (dto.email() != null && !dto.email().equals(user.getEmail())) {
            validateEmailUnique(dto.email());
            user.setEmail(dto.email());
            changed = true;
        }

        if (dto.password() != null && !dto.password().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.password()));
            changed = true;
        }

        if (changed) {
            save(user);
        }
    }

    public void deleteUser(UUID id) {
        userRepository.delete(findUserByIdOrThrow(id));
    }

    private void validateEmailUnique(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new ServiceException(ServiceError.EMAIL_ALREADY_EXISTS, email);
        }
    }
}