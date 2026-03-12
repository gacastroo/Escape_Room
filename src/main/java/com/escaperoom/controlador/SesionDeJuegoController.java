package com.escaperoom.controlador;

import com.escaperoom.modelo.SalaEscape;
import com.escaperoom.modelo.SesionDeJuego;
import com.escaperoom.servicio.SesionDeJuegoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sesiones")
public class SesionDeJuegoController {

    private final SesionDeJuegoService service;

    public SesionDeJuegoController(SesionDeJuegoService service){
        this.service = service;
    }

    @GetMapping
    public List<SesionDeJuego> obtenerTodas(){
        return service.obtenerTodas();
    }

    @PostMapping
    public SesionDeJuego crearSesion(@RequestBody SesionDeJuego sesion){
        return service.guardar(sesion);
    }

    @GetMapping("/id/{id}")
    public SesionDeJuego obtenerPorId(@PathVariable Long id){
        return service.obtenerPorId(id);
    }

    @GetMapping("/nombre/{nombre}")
    public SesionDeJuego obtenerPorSala(@PathVariable SalaEscape nombre){
        return service.findBySala(nombre);
    }

    @PutMapping("/actualizar/{id}")
    public SesionDeJuego actualizarSesion(@PathVariable Long id, @RequestBody SesionDeJuego sesion){
        return service.actualizarSesion(id, sesion);
    }


    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        service.eliminar(id);
    }
}