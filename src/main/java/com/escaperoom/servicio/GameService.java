package com.escaperoom.servicio;
import com.escaperoom.modelo.*;
import com.escaperoom.repositorio.SalaEscapeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GameService {

    private final EscapeRoomLoaderService loaderService;
    private final SalaEscapeRepository salaRepository;
    private final SimpMessagingTemplate messagingTemplate;

    private final Map<Long, GameState> estados = new HashMap<>();

    public void iniciarPartida(SalaEscape sala) {

        EscapeRoomConfig config = loaderService.cargarEscape(sala.getTematica());

        GameState estado = new GameState();

        if (!config.getPuzzles().isEmpty()) {
            estado.getPuzzlesDisponibles().add(config.getPuzzles().get(0).getId());
        }

        estados.put(sala.getId(), estado);

        sala.setTiempoInicio(LocalDateTime.now());
        sala.setEnJuego(true);

        salaRepository.save(sala);
    }

    public void procesarAccion(AccionJugador accion) {

        SalaEscape sala = salaRepository
                .findById(accion.getSalaId())
                .orElseThrow();

        // comprobar si se acabó el tiempo
        if (calcularTiempoRestante(sala) <= 0) {

            messagingTemplate.convertAndSend(
                    "/topic/sala/" + sala.getId(),
                    "TIEMPO_AGOTADO"
            );

            return;
        }

        GameState estado = estados.get(sala.getId());

        EscapeRoomConfig config = loaderService.cargarEscape(sala.getTematica());

        Optional<Puzzle> puzzleOpt = config.getPuzzles()
                .stream()
                .filter(p -> p.getId().equals(accion.getPuzzleId()))
                .findFirst();

        if (puzzleOpt.isEmpty()) {
            return;
        }

        Puzzle puzzle = puzzleOpt.get();

        synchronized (estado) {

            // puzzle no disponible
            if (!estado.getPuzzlesDisponibles().contains(puzzle.getId())) {
                return;
            }

            // puzzle ya resuelto
            if (estado.getPuzzlesResueltos().contains(puzzle.getId())) {
                return;
            }

            // respuesta incorrecta
            if (!puzzle.getRespuesta().equalsIgnoreCase(accion.getRespuesta())) {

                messagingTemplate.convertAndSend(
                        "/topic/sala/" + sala.getId(),
                        "RESPUESTA_INCORRECTA"
                );

                return;
            }

            // marcar puzzle como resuelto
            estado.getPuzzlesResueltos().add(puzzle.getId());
            estado.getPuzzlesDisponibles().remove(puzzle.getId());

            // desbloquear nuevos puzzles
            if (puzzle.getDesbloquea() != null) {
                estado.getPuzzlesDisponibles().addAll(puzzle.getDesbloquea());
            }

            // comprobar victoria
            if (estado.getPuzzlesResueltos().size() == config.getPuzzles().size()) {

                messagingTemplate.convertAndSend(
                        "/topic/sala/" + sala.getId(),
                        "ESCAPE_COMPLETADO"
                );
            }

            enviarEstado(sala, estado);
        }
    }

    private void enviarEstado(SalaEscape sala, GameState estado) {

        long tiempoRestante = calcularTiempoRestante(sala);

        if (tiempoRestante < 0) {
            tiempoRestante = 0;
        }

        GameUpdate update = new GameUpdate();
        update.setPuzzlesResueltos(new ArrayList<>(estado.getPuzzlesResueltos()));
        //update.setPuzzlesDisponibles(new ArrayList<>(estado.getPuzzlesDisponibles()));
        update.setTiempoRestante(tiempoRestante);

        messagingTemplate.convertAndSend(
                "/topic/sala/" + sala.getId(),
                update
        );
    }

    private long calcularTiempoRestante(SalaEscape sala) {

        EscapeRoomConfig config = loaderService.cargarEscape(sala.getTematica());

        long tiempoMaximo = config.getTiempoMaximo();

        long tiempoTranscurrido = Duration.between(
                sala.getTiempoInicio(),
                LocalDateTime.now()
        ).toSeconds();

        return tiempoMaximo - tiempoTranscurrido;
    }
}