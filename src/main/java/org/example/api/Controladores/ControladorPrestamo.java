package org.example.api.Controladores;

import jakarta.validation.Valid;
import org.example.api.Modelo.Prestamo;
import org.example.api.Repositorios.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/prestamos")
public class ControladorPrestamo {

    private final PrestamoRepository prestamoRepository;

    @Autowired
    public ControladorPrestamo(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    // GET: Obtener todos los préstamos
    @GetMapping
    public ResponseEntity<List<Prestamo>> getPrestamos() {
        return ResponseEntity.ok(prestamoRepository.findAll());
    }

    // GET: Obtener préstamo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> getPrestamo(@PathVariable Integer id) {
        return prestamoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: Insertar préstamo
    @PostMapping("/prestamo")
    public ResponseEntity<Prestamo> addPrestamo(@Valid @RequestBody Prestamo prestamo) {
        Prestamo prestamoPersistido = prestamoRepository.save(prestamo);
        return ResponseEntity.ok().body(prestamoPersistido);
    }

    // PUT: Actualizar préstamo
    @PutMapping("/{id}")
    public ResponseEntity<Prestamo> updatePrestamo(@PathVariable Integer id,
                                                   @RequestParam String fechaDevolucion) {
        Optional<Prestamo> prestamoExistente = prestamoRepository.findById(id);
        if (prestamoExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Prestamo prestamoActualizado = prestamoExistente.get();
        prestamoActualizado.setFechaDevolucion(LocalDate.parse(fechaDevolucion));

        prestamoRepository.save(prestamoActualizado);
        return ResponseEntity.ok(prestamoActualizado);
    }

    // DELETE: Eliminar préstamo
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePrestamo(@PathVariable Integer id) {
        prestamoRepository.deleteById(id);
        return ResponseEntity.ok("Préstamo con ID: " + id + " eliminado");
    }
}
