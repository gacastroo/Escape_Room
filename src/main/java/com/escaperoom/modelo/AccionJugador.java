package com.escaperoom.modelo;

import lombok.Data;

@Data
public class AccionJugador {

    private Long salaId;
    private String jugador;
    private String puzzleId;
    private String respuesta;

}