package com.escaperoom.controlador;

import com.escaperoom.modelo.AccionJugador;
import com.escaperoom.servicio.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @MessageMapping("/juego/responder")
    public void procesarAccion(AccionJugador accion) {

        gameService.procesarAccion(accion);
    }


}
