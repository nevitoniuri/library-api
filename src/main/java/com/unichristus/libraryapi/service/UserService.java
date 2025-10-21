package com.unichristus.libraryapi.service;

import com.unichristus.libraryapi.dto.request.UserCreateRequestDTO;
import com.unichristus.libraryapi.dto.request.UserUpdateRequestDTO;
import com.unichristus.libraryapi.exception.ServiceError;
import com.unichristus.libraryapi.exception.ServiceException;
import com.unichristus.libraryapi.model.User;
import com.unichristus.libraryapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findUserByIdOrThrow(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceError.USER_NOT_FOUND, id));
    }

    public User save(User user) {
        return userRepository.save(user);
    }


    public User createUser(UserCreateRequestDTO dto) {
        validateEmailUnique(dto.email());

        User user = User.builder()
                .name(dto.name())
                .email(dto.email())
                .password(dto.password()) // Na prática, a senha deve ser criptografada
                .build();

        return save(user);
    }

    public void updateUser(UUID id, UserUpdateRequestDTO dto) {
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
            user.setPassword(dto.password()); // Na prática, a senha deve ser criptografada
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