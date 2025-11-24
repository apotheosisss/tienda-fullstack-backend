package com.example.crud.controller;

import java.util.List;
import com.example.crud.model.Persona;
import com.example.crud.repository.PersonaRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/personas")
@CrossOrigin("*")//Permitiendo llamadas desde el FrontEnd
public class PersonaController {

    private final PersonaRepository repo;

    //Constructor

    public PersonaController(PersonaRepository repo) {
        this.repo = repo;
    }

    //Listar
    @GetMapping
    public List<Persona> findAll() {
        return repo.findAll();
    }

    //Guardar
    @PostMapping
    public Persona guardar(@RequestBody Persona persona) {
        return repo.save(persona);
    }

    //Eliminar
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repo.deleteById(id);
    }

}
