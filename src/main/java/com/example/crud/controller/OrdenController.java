package com.example.crud.controller;

import com.example.crud.dto.OrdenRequest;
import com.example.crud.model.Orden;
import com.example.crud.model.Producto;
import com.example.crud.model.Usuario;
import com.example.crud.repository.OrdenRepository;
import com.example.crud.repository.ProductoRepository;
import com.example.crud.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    private final OrdenRepository ordenRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    public OrdenController(OrdenRepository ordenRepository, ProductoRepository productoRepository, UsuarioRepository usuarioRepository) {
        this.ordenRepository = ordenRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    @Transactional // Importante: Si algo falla, se revierte todo (no se cobra ni se baja stock)
    public ResponseEntity<?> crearOrden(@RequestBody OrdenRequest request) {
        // 1. Verificar Usuario
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Procesar Stock de Productos
        for (OrdenRequest.ItemPedido item : request.getItems()) {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            if (producto.getStock() < item.getCantidad()) {
                return ResponseEntity.badRequest().body("Stock insuficiente para: " + producto.getNombre());
            }

            // Restar Stock
            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);
        }

        // 3. Crear Orden
        Orden orden = new Orden();
        orden.setUsuario(usuario);
        orden.setFecha(LocalDateTime.now());
        orden.setTotal(request.getTotal());
        orden.setEstado("COMPLETADO"); // Asumimos pago exitoso para este flujo

        Orden nuevaOrden = ordenRepository.save(orden);
        return ResponseEntity.ok(nuevaOrden);
    }

    @GetMapping("/usuario/{id}")
    public List<Orden> getOrdenesPorUsuario(@PathVariable Long id) {
        return ordenRepository.findByUsuarioId(id);
    }
}