package com.escaperoom.servicio;
import com.escaperoom.modelo.Jugador;
import com.escaperoom.modelo.SalaEscape;
import com.escaperoom.repositorio.JugadorRepository;
import com.escaperoom.repositorio.SalaEscapeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class JugadorService {

    private final JugadorRepository jugadorRepository;
    private final SalaEscapeRepository salaEscapeRepository;

    public JugadorService(JugadorRepository jugadorRepository, SalaEscapeRepository salaEscapeRepository) {
        this.jugadorRepository = jugadorRepository;
        this.salaEscapeRepository = salaEscapeRepository;
    }

    // ==============================
    // Crear jugador con nombre
    // ==============================
    public Jugador crearJugador(String nombre) {

        if (jugadorRepository.existsByNombre(nombre)) {
            throw new RuntimeException("El nombre ya está en uso");
        }

        Jugador jugador = new Jugador();
        jugador.setNombre(nombre);
        jugador.setEnJuego(false);

        return jugadorRepository.save(jugador);
    }

    // ==============================
    // Editar nombre del jugador
    // ==============================
    public Jugador editarNombre(Long jugadorId, String nuevoNombre) {

        if (jugadorRepository.existsByNombre(nuevoNombre)) {
            throw new RuntimeException("Ese nombre ya está en uso");
        }

        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

        jugador.setNombre(nuevoNombre);

        return jugadorRepository.save(jugador);
    }

    // ==============================
    // Crear jugador anónimo
    // ==============================
    public Jugador crearJugadorAnonimo() {

        Jugador jugador = new Jugador();
        jugador.setNombre(generarNombreAnonimo());
        jugador.setEnJuego(false);

        return jugadorRepository.save(jugador);
    }


    // ==============================
    // Generar nombre anónimo único
    // ==============================
    private String generarNombreAnonimo() {

        String nombre;

        do {
            int numero = 1000 + (int) (Math.random() * 9000);
            nombre = "Anonimo-" + numero;

        } while (jugadorRepository.existsByNombre(nombre));

        return nombre;
    }

    // ==============================
    // Obtener todas las salas disponibles
    // ==============================
    public List<SalaEscape> listarSalas() {
        return salaEscapeRepository.findAll();
    }
}