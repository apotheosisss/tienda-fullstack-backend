package com.example.crud.controller;

import com.example.crud.model.Usuario;
import com.example.crud.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @PostMapping
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @PutMapping("/{email}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable String email, @RequestBody Usuario detalles) {
        return usuarioRepository.findByEmail(email)
                .map(usuario -> {
                    usuario.setNombre(detalles.getNombre());
                    // Solo actualizamos contraseña si viene una nueva
                    if (detalles.getPassword() != null && !detalles.getPassword().isEmpty()) {
                        usuario.setPassword(passwordEncoder.encode(detalles.getPassword()));
                    }
                    // Solo Admin puede cambiar roles, aseguramos que no se pierda
                    if (detalles.getRol() != null) {
                        usuario.setRol(detalles.getRol());
                    }
                    return ResponseEntity.ok(usuarioRepository.save(usuario));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}