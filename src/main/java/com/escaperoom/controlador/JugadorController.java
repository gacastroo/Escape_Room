package com.escaperoom.controlador;

import com.escaperoom.modelo.Jugador;
import com.escaperoom.modelo.SalaConEstado;
import com.escaperoom.servicio.JugadorService;
import com.escaperoom.servicio.SalaEscapeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/jugadores")
public class JugadorController {

    private final JugadorService jugadorService;
    private final SalaEscapeService salaService;

    public JugadorController(JugadorService jugadorService,
                             SalaEscapeService salaService) {
        this.jugadorService = jugadorService;
        this.salaService = salaService;
    }

    // ==============================
    // Listar todas las salas con estado
    // ==============================
    @GetMapping("/salas")
    public List<SalaConEstado> listarSalas() {
        return jugadorService.listarSalas().stream()
                .map(sala -> new SalaConEstado(
                        sala.getId(),
                        sala.getNombre(),
                        sala.getJugadoresConectados().size(),
                        sala.getJugadoresConectados().size() >= 8,
                        sala.isEnJuego(),
                        salaService.obtenerEstadoSala(sala.getId())
                ))
                .collect(Collectors.toList());
    }

    // ==============================
    // Crear jugador con nombre
    // ==============================
    @PostMapping
    public Jugador crearJugador(@RequestParam @Valid String nombre) {
        return jugadorService.crearJugador(nombre);
    }

    // ==============================
    // Editar nombre
    // ==============================
    @PutMapping("/{id}")
    public Jugador editarNombre(@PathVariable @Valid Long id,
                                @RequestParam String nuevoNombre) {
        return jugadorService.editarNombre(id, nuevoNombre);
    }

    // ==============================
    // Crear jugador anónimo
    // ==============================
    @PostMapping("/anonimo")
    public Jugador crearJugadorAnonimo() {
        return jugadorService.crearJugadorAnonimo();
    }
}