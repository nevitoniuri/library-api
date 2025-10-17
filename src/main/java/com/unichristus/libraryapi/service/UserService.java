package com.unichristus.libraryapi.service;

import com.unichristus.libraryapi.exception.ServiceError;
import com.unichristus.libraryapi.exception.ServiceException;
import com.unichristus.libraryapi.model.User;
import com.unichristus.libraryapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User findUserByIdOrThrow(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceError.USER_NOT_FOUND, id));
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user) {
        // Check if user exists before updating
        if (userRepository.existsById(user.getId())) {
            return userRepository.save(user);
        }
        return null; // or throw an exception
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}

