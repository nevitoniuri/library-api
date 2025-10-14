package com.unichristus.libraryapi.service;


import com.unichristus.libraryapi.model.Usuario;
import com.unichristus.libraryapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario cadastrarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }
    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }
    public Usuario atualizarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
