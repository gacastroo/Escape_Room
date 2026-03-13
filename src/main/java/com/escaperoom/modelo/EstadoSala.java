package com.escaperoom.modelo;

public enum EstadoSala {
    ABIERTA,         // aún pueden unirse jugadores
    LISTA_PARA_INICIAR, // se puede iniciar, aún no ha comenzado
    EN_JUEGO         // ya se inició la partida
}
