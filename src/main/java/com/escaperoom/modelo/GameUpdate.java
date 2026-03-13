package com.escaperoom.modelo;
import lombok.Data;

import java.util.List;

@Data
public class GameUpdate {

    private List<Puzzle> puzzlesDisponibles;
    private List<String> puzzlesResueltos;
    private long tiempoRestante;

}