package com.escaperoom.controlador;
import com.escaperoom.modelo.Jugador;
import com.escaperoom.modelo.SalaEscape;
import com.escaperoom.servicio.SalaEscapeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salas")
public class SalaEscapeController {

    private final SalaEscapeService salaService;

    public SalaEscapeController(SalaEscapeService salaService) {
        this.salaService = salaService;
    }

    // ==============================
    // Crear sala
    // ==============================
    @PostMapping
    public SalaEscape crearSala(@RequestParam @Valid String nombre) {
        return salaService.crearSala(nombre);
    }

    // ==============================
    // Unirse a sala
    // ==============================
    @PostMapping("/{salaId}/unirse")
    public SalaEscape unirseSala(@PathVariable @Valid Long salaId,
                                 @RequestParam Long jugadorId) {
        return salaService.unirseSala(salaId, jugadorId);
    }

    // ==============================
    // Iniciar juego
    // ==============================
    @PostMapping("/{salaId}/iniciar")
    public SalaEscape iniciarJuego(@PathVariable Long salaId) {
        return salaService.iniciarJuego(salaId);
    }

    // ==============================
    // Ver jugadores de sala
    // ==============================
    @GetMapping("/{salaId}/jugadores")
    public List<Jugador> verJugadores(@PathVariable Long salaId) {
        return salaService.obtenerJugadores(salaId);
    }
}
