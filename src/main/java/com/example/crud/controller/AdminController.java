package com.example.crud.controller;

import com.example.crud.model.Orden;
import com.example.crud.model.Usuario;
import com.example.crud.repository.OrdenRepository;
import com.example.crud.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UsuarioRepository usuarioRepository;
    private final OrdenRepository ordenRepository;

    public AdminController(UsuarioRepository usuarioRepository, OrdenRepository ordenRepository) {
        this.usuarioRepository = usuarioRepository;
        this.ordenRepository = ordenRepository;
    }

    // --- GESTIÓN DE USUARIOS ---
    @GetMapping("/usuarios")
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    // --- GESTIÓN DE ÓRDENES ---
    @GetMapping("/ordenes")
    public List<Orden> getAllOrdenes() {
        return ordenRepository.findAll();
    }

    @PutMapping("/ordenes/{id}/estado")
    public ResponseEntity<Orden> updateEstadoOrden(@PathVariable Long id, @RequestBody String nuevoEstado) {
        return ordenRepository.findById(id)
                .map(orden -> {
                    orden.setEstado(nuevoEstado);
                    return ResponseEntity.ok(ordenRepository.save(orden));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}