package com.escaperoom.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
public class SalaEscape {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String tematica;
    @Min(value = 1, message = "El valor de la dificultad no puede ser menor que 1")
    @Max(value = 10, message = "El valor de la dificultad no puede ser mayor que 10")
    int dificultad;
    @Min(value = 0, message = "El tiempo no puede ser negativo")
    int tiempoMaximoMinutos;
}
