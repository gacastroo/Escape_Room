package com.escaperoom.modelo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SesionDeJuego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fechaHoraInicio;
    private boolean logroEscapar;
    private int minutosTardados;
    private int pistasUtilizadas;
    private int puntuacionMinutos;
    @ManyToOne
    private SalaEscape sala;
    @ManyToOne
    private Equipo equipo;
    @ManyToOne
    private Empleado gameMaster;
}