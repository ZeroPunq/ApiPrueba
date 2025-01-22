package org.example.api.Controladores;



import jakarta.validation.Valid;
import org.example.api.Modelo.Ejemplar;
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
        return ejemplarRepository.findById(String.valueOf(id))
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
    public ResponseEntity<Ejemplar> updateEjemplar(@PathVariable Integer id,
                                                   @RequestParam String estado) {
        Optional<Ejemplar> ejemplarExistente = ejemplarRepository.findById(String.valueOf(id));
        if (ejemplarExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Ejemplar ejemplarActualizado = ejemplarExistente.get();
        ejemplarActualizado.setEstado(estado);

        ejemplarRepository.save(ejemplarActualizado);
        return ResponseEntity.ok(ejemplarActualizado);
    }

    // DELETE: Eliminar ejemplar
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEjemplar(@PathVariable Integer id) {
        ejemplarRepository.deleteById(String.valueOf(id));
        return ResponseEntity.ok("Ejemplar con ID: " + id + " eliminado");
    }
}
