package com.escaperoom.modelo;

import lombok.Data;

@Data
public class LobbyMessage {

    private String tipo;
    private String jugador;
    private String mensaje;

}