package com.unichristus.libraryapi.security;

import com.unichristus.libraryapi.exception.ServiceError;
import com.unichristus.libraryapi.exception.ServiceException;
import com.unichristus.libraryapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(CustomUserDetails::from)
                .orElseThrow(() -> new ServiceException(ServiceError.USER_NOT_FOUND, email));
    }

    public CustomUserDetails loadUserById(UUID userId) {
        return userRepository.findById(userId)
                .map(CustomUserDetails::from)
                .orElseThrow(() -> new ServiceException(ServiceError.USER_NOT_FOUND, userId));
    }

}
