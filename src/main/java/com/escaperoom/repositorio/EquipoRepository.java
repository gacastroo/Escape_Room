package com.escaperoom.repositorio;

import com.escaperoom.modelo.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {
}