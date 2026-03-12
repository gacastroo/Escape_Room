package com.escaperoom.servicio;
import com.escaperoom.modelo.Equipo;
import com.escaperoom.modelo.SesionDeJuego;
import com.escaperoom.repositorio.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipoService {

    private final EquipoRepository equipoRepository;

    public EquipoService(EquipoRepository equipoRepository){
        this.equipoRepository = equipoRepository;
    }

    public List<Equipo> obtenerEquipos(){
        return equipoRepository.findAll(); //
    }

    public Equipo guardarEquipo(Equipo equipo){
        return equipoRepository.save(equipo);
    }
}
