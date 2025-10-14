package com.unichristus.libraryapi.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
public class UsuarioDTO {
    private Long id;
    @NotNull (message = "Nome do usuario e obrigatorio")
    private String nome;
    @NotNull(message = "Email do usuario e obrigatorio")
    private String email;
    @NotNull(message = "Senha do usuario e obrigatorio")
    private String senha;
}
