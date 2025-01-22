package org.example.api.Repositorios;

import org.example.api.Modelo.Ejemplar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EjemplarRepository extends JpaRepository<Ejemplar, Integer> {
}
