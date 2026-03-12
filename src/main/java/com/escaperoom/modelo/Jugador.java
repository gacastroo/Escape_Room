package com.escaperoom.modelo;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreCompletoJugador;
    @ManyToMany
    @JoinTable(
            name = "equipo_jugador",
            joinColumns = @JoinColumn(name = "jugador_id"),
            inverseJoinColumns = @JoinColumn(name = "equipo_id")
    )
    private List<Equipo> equipos;
    TipoJugador tipoJugador;
}
