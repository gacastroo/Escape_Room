package com.escaperoom.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Sala")
@Data
public class SalaEscape {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la sala es obligatorio")
    @Column(nullable = false, unique = true)
    private String nombre;

    @Enumerated(EnumType.STRING)
    private TipoTematica tematica;

    @OneToMany(mappedBy = "sala")
    private List<Jugador> jugadoresConectados = new ArrayList<>();
    private boolean enJuego = false;
    private LocalDateTime tiempoInicio = LocalDateTime.now();
    private LocalDateTime tiempoFinal = LocalDateTime.now();
    @ElementCollection
    private List<String> puzzlesResueltos = new ArrayList<>();
    @ElementCollection
    private List<String> puzzlesDisponibles = new ArrayList<>();

}