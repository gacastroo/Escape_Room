package com.escaperoom.modelo;

import lombok.Data;

import java.util.List;

@Data
public class EscapeRoomConfig {

    private int tiempoMaximo;
    private List<Puzzle> puzzles;

}