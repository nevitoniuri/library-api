package com.unichristus.libraryapi.service;

import com.unichristus.libraryapi.dto.request.UserRequestDTO;
import com.unichristus.libraryapi.exception.ServiceError;
import com.unichristus.libraryapi.exception.ServiceException;
import com.unichristus.libraryapi.model.User;
import com.unichristus.libraryapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findUserByIdOrThrow(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceError.USER_NOT_FOUND, id));
    }

    @Transactional
    public User createUser(UserRequestDTO dto) {
        validateEmailUnique(dto.email());

        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        // IMPORTANTE: Nunca armazene senhas em texto plano.
        // Use uma biblioteca de criptografia.
        user.setPassword(dto.password());

        return userRepository.save(user);
    }

    @Transactional
    public void updateUser(UUID id, UserRequestDTO dto) {
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
            user.setPassword(dto.password());
            changed = true;
        }

        if (changed) {
            userRepository.save(user);
        }
    }

    @Transactional
    public void deleteUser(UUID id) {
        // Garante que o usuário existe antes de tentar deletar
        User user = findUserByIdOrThrow(id);
        userRepository.delete(user);
    }

    private void validateEmailUnique(String email) {
        if (userRepository.existsByEmail(email)) {
            // Assumindo que você tem um erro para isso no meu ServiceError
            throw new ServiceException(ServiceError.EMAIL_ALREADY_EXISTS, email);
        }
    }
}