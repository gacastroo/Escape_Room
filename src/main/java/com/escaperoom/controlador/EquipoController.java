package com.escaperoom.controlador;
import com.escaperoom.servicio.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EquipoController {
    @Autowired
    private EquipoService service;

}
