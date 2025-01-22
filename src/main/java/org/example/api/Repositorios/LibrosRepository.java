package org.example.api.Repositorios;

import org.example.api.Modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibrosRepository extends JpaRepository<Libro, String> {
}
