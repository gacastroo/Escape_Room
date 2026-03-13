package com.escaperoom.servicio;

import com.escaperoom.modelo.EscapeRoomConfig;
import com.escaperoom.modelo.TipoTematica;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class EscapeRoomLoaderService {

    private final ObjectMapper mapper = new ObjectMapper();

    public EscapeRoomConfig cargarEscape(TipoTematica tematica) {

        try {

            String archivo = "escapes/" + tematica.name().toLowerCase() + ".json";

            InputStream input = new ClassPathResource(archivo).getInputStream();

            return mapper.readValue(input, EscapeRoomConfig.class);

        } catch (Exception e) {

            throw new RuntimeException("Error cargando escape room: " + tematica, e);

        }
    }
}