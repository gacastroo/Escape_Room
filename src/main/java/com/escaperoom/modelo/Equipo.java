package com.escaperoom.modelo;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Equipo")
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreEquipo;
    private int numeroIntegrantes;
    @ManyToMany(mappedBy = "equipos")
    private List<Jugador> jugadores;
}
