package com.escaperoom.servicio;

import com.escaperoom.modelo.EstadoSala;
import com.escaperoom.modelo.Jugador;
import com.escaperoom.modelo.SalaEscape;
import com.escaperoom.modelo.TipoTematica;
import com.escaperoom.repositorio.JugadorRepository;
import com.escaperoom.repositorio.SalaEscapeRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class SalaEscapeService {

    private final SalaEscapeRepository salaRepository;
    private final JugadorRepository jugadorRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final JugadorService jugadorService;

    public SalaEscapeService(SalaEscapeRepository salaRepository,
                             JugadorRepository jugadorRepository,
                             SimpMessagingTemplate messagingTemplate,
                             JugadorService jugadorService) {
        this.salaRepository = salaRepository;
        this.jugadorRepository = jugadorRepository;
        this.messagingTemplate = messagingTemplate;
        this.jugadorService = jugadorService;
    }

    // ==============================
    // Crear sala con nombre único
    // ==============================
    public SalaEscape crearSala(String nombre) {
        if (salaRepository.existsByNombre(nombre)) {
            throw new RuntimeException("Ya existe una sala con el nombre: " + nombre);
        }

        SalaEscape sala = new SalaEscape();
        sala.setNombre(nombre);

        // Elegir temática aleatoria
        TipoTematica[] tematicas = TipoTematica.values();
        sala.setTematica(tematicas[new Random().nextInt(tematicas.length)]);

        sala.setEnJuego(false); // partida no iniciada
        return salaRepository.save(sala);
    }

    // ==============================
    // Unirse a sala
    // ==============================
    public SalaEscape unirseSala(Long salaId, Long jugadorId) {
        SalaEscape sala = salaRepository.findById(salaId)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));

        if (sala.isEnJuego()) {
            throw new RuntimeException("No se puede unir, la partida ya comenzó");
        }

        if (sala.getJugadoresConectados().size() >= 4) {
            throw new RuntimeException("La sala ya está llena (4 jugadores máximo)");
        }

        Jugador jugador;
        if (jugadorId == null) { // jugador anónimo
            jugador = jugadorService.crearJugadorAnonimo();
        } else {
            jugador = jugadorRepository.findById(jugadorId)
                    .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
        }

        if (!sala.getJugadoresConectados().contains(jugador)) {
            sala.getJugadoresConectados().add(jugador);
            jugador.setSala(sala);

        }

        salaRepository.save(sala);

        // Notificación por WebSocket solo a la sala
        messagingTemplate.convertAndSend(
                "/topic/lobby/" + sala.getId(),
                jugador.getNombre() + " se ha unido a la sala"
        );

        return sala;
    }

    // ==============================
    // Ver si la sala puede iniciar partida
    // ==============================
    public boolean sePuedeIniciar(Long salaId) {
        SalaEscape sala = salaRepository.findById(salaId)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));

        // La partida puede iniciarse si hay al menos 4 jugadores y no ha empezado
        return !sala.isEnJuego() && sala.getJugadoresConectados().size() >= 2;
    }

    // ==============================
    // Iniciar juego (solo si se puede)
    // ==============================
    public SalaEscape iniciarJuego(Long salaId) {
        if (!sePuedeIniciar(salaId)) {
            throw new RuntimeException("No se puede iniciar la partida aún");
        }

        SalaEscape sala = salaRepository.findById(salaId)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));

        sala.setEnJuego(true);
        salaRepository.save(sala);

        // Notificar a la sala que el juego comenzó
        messagingTemplate.convertAndSend(
                "/topic/lobby/" + sala.getId(),
                "La partida ha comenzado en la sala: " + sala.getNombre()
        );

        return sala;
    }

    // ==============================
    // Obtener jugadores de la sala
    // ==============================
    public List<Jugador> obtenerJugadores(Long salaId) {
        SalaEscape sala = salaRepository.findById(salaId)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));

        return sala.getJugadoresConectados();
    }

    // ==============================
    // Obtener estado de la sala
    // ==============================
    public EstadoSala obtenerEstadoSala(Long salaId) {
        SalaEscape sala = salaRepository.findById(salaId)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));

        if (sala.isEnJuego()) return EstadoSala.EN_JUEGO;
        if (sala.getJugadoresConectados().size() >= 4) return EstadoSala.LISTA_PARA_INICIAR;
        return EstadoSala.ABIERTA;
    }
}