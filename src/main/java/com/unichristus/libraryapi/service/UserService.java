package com.unichristus.libraryapi.service;

import com.unichristus.libraryapi.model.User;
import com.unichristus.libraryapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
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

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

