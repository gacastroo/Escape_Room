package com.escaperoom.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalaConEstado {
    private Long id;
    private String nombre;
    private int jugadoresConectados;
    private boolean llena;
    private boolean enJuego;
    private EstadoSala estadoSala;
}