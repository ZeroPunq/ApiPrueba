package org.example.api.Controladores;



import jakarta.validation.Valid;
import org.example.api.Modelo.Ejemplar;
import org.example.api.Modelo.Prestamo;
import org.example.api.Repositorios.EjemplarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ejemplares")
public class ControladorEjemplar {

    private final EjemplarRepository ejemplarRepository;

    @Autowired
    public ControladorEjemplar(EjemplarRepository ejemplarRepository) {
        this.ejemplarRepository = ejemplarRepository;
    }

    // GET: Obtener todos los ejemplares
    @GetMapping
    public ResponseEntity<List<Ejemplar>> getEjemplares() {
        return ResponseEntity.ok(ejemplarRepository.findAll());
    }

    // GET: Obtener ejemplar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Ejemplar> getEjemplar(@PathVariable Integer id) {
        return ejemplarRepository.findById(Integer.valueOf(String.valueOf(id)))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: Insertar ejemplar
    @PostMapping("/ejemplar")
    public ResponseEntity<Ejemplar> addEjemplar(@Valid @RequestBody Ejemplar ejemplar) {
        Ejemplar ejemplarPersistido = ejemplarRepository.save(ejemplar);
        return ResponseEntity.ok().body(ejemplarPersistido);
    }

    // PUT: Actualizar ejemplar
    @PutMapping("/{id}")
    public ResponseEntity<Ejemplar> update(@Valid @RequestBody Ejemplar ejemplar, @PathVariable int id) {
        Ejemplar persistido = ejemplarRepository.save(ejemplar);
        return ResponseEntity.ok().body(persistido);
    }

    // DELETE: Eliminar ejemplar
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEjemplar(@PathVariable Integer id) {
        ejemplarRepository.deleteById(id);
        return ResponseEntity.ok("Ejemplar con ID: " + id + " eliminado");
    }
}
