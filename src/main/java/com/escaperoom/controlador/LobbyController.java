package com.escaperoom.controlador;
import com.escaperoom.modelo.LobbyMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class LobbyController {

    @MessageMapping("/entrar")
    @SendTo("/topic/lobby")
    public LobbyMessage entrarSala(LobbyMessage mensaje) {

        mensaje.setMensaje(mensaje.getJugador() + " se ha unido a la sala");

        return mensaje;
    }

}