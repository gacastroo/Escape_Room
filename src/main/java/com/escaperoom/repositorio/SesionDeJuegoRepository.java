package com.escaperoom.repositorio;

import com.escaperoom.modelo.SalaEscape;
import com.escaperoom.modelo.SesionDeJuego;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SesionDeJuegoRepository extends JpaRepository<SesionDeJuego, Long> {

    SesionDeJuego findBySala(SalaEscape salaEscape);
}