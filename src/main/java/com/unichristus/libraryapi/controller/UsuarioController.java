package com.unichristus.libraryapi.controller;

import com.unichristus.libraryapi.model.Usuario;
import com.unichristus.libraryapi.model.dto.UsuarioDTO;
import com.unichristus.libraryapi.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());

        Usuario savedUsuario = usuarioService.cadastrarUsuario(usuario);
        return ResponseEntity.ok(savedUsuario);
    }
    @GetMapping ("/{id}")
    public ResponseEntity<Optional<Usuario>> buscarUsuarioPorId(@PathVariable Long id){
        Optional<Usuario> usuario = usuarioService.buscarUsuarioPorId(id);
        return usuario.isPresent() ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }
    @GetMapping
    public List<Usuario> listarTodosUsuarios(){
        return usuarioService.listarTodosUsuarios();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody @Valid UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());

        Usuario updatedUsuario = usuarioService.atualizarUsuario(usuario);
        return ResponseEntity.ok(updatedUsuario);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

}










