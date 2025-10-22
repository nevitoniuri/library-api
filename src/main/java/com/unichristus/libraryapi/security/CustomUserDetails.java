package com.unichristus.libraryapi.security;

import com.unichristus.libraryapi.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private UUID id;
    private String name;
    private String email;
    private String password;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CustomUserDetails from(User user) {
        return new CustomUserDetails(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public static User toEntity(CustomUserDetails userDetails) {
        User user = new User();
        user.setId(userDetails.getId());
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setActive(userDetails.isActive());
        user.setCreatedAt(userDetails.getCreatedAt());
        user.setUpdatedAt(userDetails.getUpdatedAt());
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Role.USER.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    //TODO Implementar lógica de expiração, bloqueio e credenciais se necessário
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}