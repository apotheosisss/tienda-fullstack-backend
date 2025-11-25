package com.example.crud.controller;

import com.example.crud.model.Orden;
import com.example.crud.repository.OrdenRepository;
import com.example.crud.repository.ProductoRepository;
import com.example.crud.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/dashboard")
public class DashboardController {

    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final OrdenRepository ordenRepository;

    public DashboardController(UsuarioRepository usuarioRepository, ProductoRepository productoRepository, OrdenRepository ordenRepository) {
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.ordenRepository = ordenRepository;
    }

    @GetMapping("/stats")
    public Map<String, Object> getDashboardStats() {
        long totalUsuarios = usuarioRepository.count();
        long totalProductos = productoRepository.count();
        List<Orden> ordenes = ordenRepository.findAll();
        long totalOrdenes = ordenes.size();

        // Calcular ventas totales sumando el total de cada orden
        double ventasTotales = ordenes.stream()
                .mapToDouble(Orden::getTotal)
                .sum();

        Map<String, Object> stats = new HashMap<>();
        stats.put("usuarios", totalUsuarios);
        stats.put("productos", totalProductos);
        stats.put("ordenes", totalOrdenes);
        stats.put("ventas", ventasTotales);

        return stats;
    }
}