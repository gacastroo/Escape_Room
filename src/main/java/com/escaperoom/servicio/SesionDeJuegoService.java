package com.escaperoom.servicio;

import com.escaperoom.modelo.SalaEscape;
import com.escaperoom.modelo.SesionDeJuego;
import com.escaperoom.repositorio.SesionDeJuegoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SesionDeJuegoService {

    private final SesionDeJuegoRepository repository;

    public SesionDeJuegoService(SesionDeJuegoRepository repository) {
        this.repository = repository;
    }

    public List<SesionDeJuego> obtenerTodas() {
        return repository.findAll();
    }

    public SesionDeJuego findBySala(SalaEscape salaEscape){
        return repository.findBySala(salaEscape);
    }

    public SesionDeJuego obtenerPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public SesionDeJuego guardar(SesionDeJuego sesion) {
        return repository.save(sesion);
    }

    public SesionDeJuego actualizarSesion(Long id, SesionDeJuego sesionActualizada) {
        return repository.findById(id)
                .map(sesion -> {
                    sesion.setFechaHoraInicio(sesionActualizada.getFechaHoraInicio());
                    sesion.setLogroEscapar(sesionActualizada.isLogroEscapar());
                    sesion.setMinutosTardados(sesionActualizada.getMinutosTardados());
                    sesion.setPistasUtilizadas(sesionActualizada.getPistasUtilizadas());
                    sesion.setPuntuacionMinutos(sesionActualizada.getPuntuacionMinutos());
                    sesion.setSala(sesionActualizada.getSala());
                    sesion.setEquipo(sesionActualizada.getEquipo());
                    sesion.setGameMaster(sesionActualizada.getGameMaster());
                    return repository.save(sesion);
                })
                .orElse(null);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}