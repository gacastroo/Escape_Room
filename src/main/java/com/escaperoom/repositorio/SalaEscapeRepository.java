package com.escaperoom.repositorio;

import com.escaperoom.modelo.SalaEscape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaEscapeRepository extends JpaRepository<SalaEscape, Long> {

    //List<SalaEscape> findByDificultad(int dificultad);
    //List<SalaEscape> findByTematica(String tematica);
    boolean existsByNombre(String nombre);


}